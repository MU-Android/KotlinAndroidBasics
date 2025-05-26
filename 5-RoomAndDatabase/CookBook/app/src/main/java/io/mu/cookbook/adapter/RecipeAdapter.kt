package io.mu.cookbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import io.mu.cookbook.databinding.RecyclerRowBinding
import io.mu.cookbook.model.Recipe
import io.mu.cookbook.view.ListFragmentDirections
import io.mu.cookbook.view.RecipeFragmentDirections

class RecipeAdapter(val recipeList : List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeHolder {
        val recylerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeHolder(recylerRowBinding)
    }

    override fun onBindViewHolder(
        holder: RecipeHolder,
        position: Int
    ) {
        holder.binding.recyclerViewTextView.text = recipeList[position].name
        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToRecipeFragment("old",id=recipeList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    class  RecipeHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}