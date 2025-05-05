package io.mu.lifecycle

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.mu.lifecycle.MainActivity
import io.mu.lifecycle.databinding.ActivityFourthBinding
import io.mu.lifecycle.databinding.ActivityMainBinding

class FourthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFourthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Toast.makeText(this@FourthActivity,"Hoşgeldiniz",Toast.LENGTH_SHORT).show()
        binding.btnSave.setOnClickListener(::save)

//        binding.btnSave.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(view: View?) {
//                this@MainActivity.save(view)
//            }
//        })
    }

    private fun save(view: View?){
        val alert = AlertDialog.Builder(this@FourthActivity)
        alert.setTitle("Kaydet")
        alert.setMessage("Kaydetmek istediğinize emin misiniz?")
        alert.setPositiveButton("Evet", {
                dialog, which ->
            Toast.makeText(this@FourthActivity,"Kayıt Edildi",Toast.LENGTH_SHORT).show()
        })
        alert.setNegativeButton("Hayır", { dialog, which ->
            Toast.makeText(this@FourthActivity,"Kayıt İptal Edildi",Toast.LENGTH_SHORT).show()
        })
        alert.show()
    }
}