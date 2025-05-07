package io.mu.superherobook

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.mu.superherobook.databinding.ActivityIntroductionBinding

class IntroductionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroductionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val adapterIntent = intent
        //adapterIntent.getSerializableExtra("superHero", SuperHero::class.java)
        //val hero = adapterIntent.getSerializableExtra("superHero") as SuperHero

        val hero = MySingleton.choosedSuperHero

        hero?.let {
            binding.imageView.setImageResource(hero.image)
            binding.textViewName.text = hero.name
            binding.textViewJob.text = hero.job
        }
    }
}