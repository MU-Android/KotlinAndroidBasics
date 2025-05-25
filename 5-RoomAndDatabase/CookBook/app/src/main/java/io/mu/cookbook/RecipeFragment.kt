package io.mu.cookbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.mu.cookbook.databinding.FragmentRecipeBinding
import java.nio.file.Files.delete

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!
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
                binding.btnDelete.isEnabled = false
                binding.btnSave.isEnabled = true
                binding.edtName.setText("")
                binding.edtIngredient.setText("")

            }else{
                binding.btnDelete.isEnabled = true
                binding.btnSave.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun save(view: View){

    }

    private fun delete(view: View){

    }

    private fun chooseImage(view: View) {
    }
}