package io.mu.superherobook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.mu.superherobook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var superHeroList : ArrayList<SuperHero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val superman = SuperHero("Superman", "Gazeteci", R.drawable.superman)
        val batman = SuperHero("Batman", "Patron", R.drawable.batman)
        val ironman = SuperHero("Iron Man", "Holding Sahibi", R.drawable.ironman)
        val hulk = SuperHero("Hulk", "Öğrenci", R.drawable.hulk)

        superHeroList = arrayListOf(superman, batman, ironman, hulk)

        val adapter = SuperHeroAdapter(superHeroList)
        binding.superHeroRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.superHeroRecyclerView.adapter = adapter
    }
}