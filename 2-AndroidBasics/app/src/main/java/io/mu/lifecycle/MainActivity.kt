package io.mu.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.mu.lifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnOpenSecondActivity.setOnClickListener(::openSecondActivity)
        Log.w("MainActivityLog", "onCreate run")
    }

    override fun onStart() {
        super.onStart()
        Log.w("MainActivityLog", "onStart run")
    }

    override fun onResume() {
        super.onResume()
        Log.w("MainActivityLog", "onResume run")
    }

    override fun onPause() {
        super.onPause()
        Log.w("MainActivityLog", "onPause run")
    }

    override fun onStop() {
        super.onStop()
        Log.w("MainActivityLog", "onStop run")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w("MainActivityLog", "onDestroy run")
    }

    private fun openSecondActivity(view: View?){

        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)

//        val userInput = binding.edtName.text.toString()
//        binding.txtName.text = "İsim: $userInput"
    }
}