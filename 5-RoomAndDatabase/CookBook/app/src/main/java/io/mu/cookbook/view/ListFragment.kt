package io.mu.cookbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import io.mu.cookbook.databinding.FragmentListBinding
import io.mu.cookbook.model.Recipe
import io.mu.cookbook.roomdb.RecipeDAO
import io.mu.cookbook.roomdb.RecipeDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : RecipeDatabase
    private lateinit var recipeDAO: RecipeDAO
    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), RecipeDatabase::class.java, "Recipes").build()
        recipeDAO = db.recipeDao()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.floatingActionButton?.setOnClickListener(::addNew)
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        getData()
    }

    private fun getData(){
        mDisposable.add(
            recipeDAO.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(::handleResponse)
        )
    }

    private fun handleResponse(recipes : List<Recipe>){
        recipes.forEach {
            println(it.name)
            println(it.ingredients)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }

    private fun addNew(view: View) {
        val action = ListFragmentDirections.actionListFragmentToRecipeFragment(information = "new", id=0)
        Navigation.findNavController(view).navigate(action)
    }
}