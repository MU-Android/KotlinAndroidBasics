package io.mu.lifecycle

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.mu.lifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("io.mu.lifecycle", MODE_PRIVATE)

        val getUsername = sharedPreferences.getString("name", "")
        binding.txtSavedName.text = getUsername

        binding.btnSave.setOnClickListener(::openCalculator)
    }

//    private fun save(view: View?){
//        val userName = binding.edtName.text.toString()
//        if (userName == ""){
//            Toast.makeText(this@MainActivity, "İsminizi Boş Bırakmayınız!", Toast.LENGTH_SHORT).show()
//        }else{
//            sharedPreferences.edit().putString("name", userName).apply()
//            binding.txtSavedName.text = userName
//        }
//
//    }
//
//    private fun delete(view: View?){
//        val getUsername = sharedPreferences.getString("name", "")
//        if (getUsername != ""){
//            sharedPreferences.edit().remove("name").apply()
//        }else {
//            Toast.makeText(this@MainActivity, "İsim Kayıtlı Değil!", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.txtSavedName.text = sharedPreferences.getString("name", "")
//    }

    private fun openCalculator(view: View?){
        startActivity(Intent(this@MainActivity, CalculatorActivity::class.java))
    }
}