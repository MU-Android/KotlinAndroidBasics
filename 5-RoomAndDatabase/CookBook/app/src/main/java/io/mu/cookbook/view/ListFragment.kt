package io.mu.cookbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.mu.cookbook.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addNew(view: View) {
        val action = ListFragmentDirections.actionListFragmentToRecipeFragment(information = "new", id=0)
        Navigation.findNavController(view).navigate(action)
    }
}