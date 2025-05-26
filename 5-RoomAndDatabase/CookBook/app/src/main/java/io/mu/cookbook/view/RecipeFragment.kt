package io.mu.cookbook.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.mu.cookbook.databinding.FragmentRecipeBinding
import androidx.navigation.Navigation
import androidx.room.Room
import io.mu.cookbook.model.Recipe
import io.mu.cookbook.roomdb.RecipeDAO
import io.mu.cookbook.roomdb.RecipeDatabase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var chooseImage: Uri? = null
    private var chooseBitmap: Bitmap? = null
    private val mDispoasble = CompositeDisposable()
    private var chooseRecipe : Recipe? = null

    private lateinit var db : RecipeDatabase
    private lateinit var recipeDAO: RecipeDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()

        db = Room.databaseBuilder(requireContext(), RecipeDatabase::class.java, "Recipes").build()
        recipeDAO = db.recipeDao()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSave.setOnClickListener(::save)
            btnDelete.setOnClickListener(::delete)
            imageView.setOnClickListener(::chooseImage)
        }

        arguments?.let{
            val information = RecipeFragmentArgs.fromBundle(it).information

            if (information == "new"){
                chooseRecipe = null
                binding.btnDelete.isEnabled = false
                binding.btnSave.isEnabled = true
                binding.edtName.setText("")
                binding.edtIngredient.setText("")

            }else{
                binding.btnDelete.isEnabled = true
                binding.btnSave.isEnabled = false
                val id = RecipeFragmentArgs.fromBundle(it).id
                mDispoasble.add(recipeDAO.findById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread())
                    .subscribe(::handleResponse))
            }
        }
    }

    private fun handleResponse(recipe : Recipe){
        binding.edtName.setText(recipe.name)
        binding.edtIngredient.setText(recipe.ingredients)
        val bitmap = BitmapFactory.decodeByteArray(recipe.image, 0, recipe.image.size)
        binding.imageView.setImageBitmap(bitmap)
        chooseRecipe = recipe
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDispoasble.clear()
    }

    private fun save(view: View){
        val name = binding.edtName.text.toString()
        val ingredient = binding.edtIngredient.text.toString()

        if(chooseBitmap != null){
            val resizedBitmap = resizeBitmap(chooseBitmap!!,300)
            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byteArray = outputStream.toByteArray()

            val recipe = Recipe(name, ingredient, byteArray)

            mDispoasble.add(recipeDAO.insert(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(::handleResponseForInsert))
        }
    }
    private fun handleResponseForInsert(){
        val action = RecipeFragmentDirections.actionRecipeFragmentToListFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun delete(view: View){
        mDispoasble.add(
            recipeDAO.delete(chooseRecipe!!)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(::handleResponseForInsert)
        )
    }

    private fun chooseImage(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "İzin Ver",
                        View.OnClickListener {

                        }).show()

                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(
                        "İzin Ver",
                        View.OnClickListener {

                        }).show()

                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }


    }

    private fun registerLauncher() {

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        chooseImage = intentFromResult.data
                        try {
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(
                                    requireActivity().contentResolver,
                                    chooseImage!!
                                )
                                chooseBitmap = ImageDecoder.decodeBitmap(source)
                            } else {
                                chooseBitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().contentResolver,
                                    chooseImage
                                )
                            }
                            binding.imageView.setImageBitmap(chooseBitmap)
                        } catch (e: Exception) {
                            println(e)
                        }
                    }
                }
            }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    Toast.makeText(requireContext(), "İzin verilmedi!", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun resizeBitmap(chooseUserBitmap: Bitmap, maximumSize: Int): Bitmap {
        var width = chooseUserBitmap.width
        var height = chooseUserBitmap.height

        val bitmapRate = width.toFloat() / height.toFloat()

        if (bitmapRate > 1){
            width = maximumSize
            val reducedHeight = width / bitmapRate
            height = reducedHeight.toInt()
        }else{
            height = maximumSize
            val reducedWidth = height * bitmapRate
            width = reducedWidth.toInt()
        }

        return Bitmap.createScaledBitmap(chooseUserBitmap, width, height, true)
//        return chooseUserBitmap.scale(width, height)
    }
}