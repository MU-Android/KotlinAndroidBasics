package io.mu.fragmentandnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.mu.fragmentandnavigation.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var binding : FragmentFirstBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.editTextName?.setText("")
        binding?.btnSecondActivity?.setOnClickListener(::next)
    }

    fun next(view : View?){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}