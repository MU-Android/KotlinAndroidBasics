package io.mu.lifecycle

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import io.mu.lifecycle.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {

    lateinit var binding : ActivityCalculatorBinding
    var firstNumber : Float? = null
    var secondNumber : Float? = null
    var result : Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAddition.setOnClickListener(::addition)
        binding.btnDivision.setOnClickListener(::division)
        binding.btnMultiplication.setOnClickListener(::multiplication)
        binding.btnSubtraction.setOnClickListener(::subtraction)

        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?,p1: Int,p2: Int,p3: Int) {}
            override fun onTextChanged(p0: CharSequence?,p1: Int,p2: Int,p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                getNumbers()
            }
        }

        binding.edtFirstNumber.addTextChangedListener(textWatcher)
        binding.edtSecondNumber.addTextChangedListener(textWatcher)
    }

    private fun addition(view: View?){
        getNumbers()?.let { (a, b) ->
            val result = a + b
            binding.txtResult.text = "Sonuç: $result"
        }
    }

    private fun division(view: View?){
        getNumbers()?.let { (a, b) ->
            val result = a / b
            binding.txtResult.text = "Sonuç: $result"
        }
    }

    private fun multiplication(view: View?){
        getNumbers()?.let { (a, b) ->
            val result = a * b
            binding.txtResult.text = "Sonuç: $result"
        }
    }

    private fun subtraction(view: View?){
        getNumbers()?.let { (a, b) ->
            val result = a - b
            binding.txtResult.text = "Sonuç: $result"
        }
    }

    private fun getNumbers(): Pair<Float, Float>? {
        val num1 = binding.edtFirstNumber.text.toString().toFloatOrNull()
        val num2 = binding.edtSecondNumber.text.toString().toFloatOrNull()
        return if (num1 != null && num2 != null) {
            enableOperationButtons(true)
            Pair(num1, num2)
        }else {
            enableOperationButtons(false)
            null
        }
    }

    private fun enableOperationButtons(enabled: Boolean) {
        binding.btnAddition.isEnabled = enabled
        binding.btnDivision.isEnabled = enabled
        binding.btnMultiplication.isEnabled = enabled
        binding.btnSubtraction.isEnabled = enabled
    }
}