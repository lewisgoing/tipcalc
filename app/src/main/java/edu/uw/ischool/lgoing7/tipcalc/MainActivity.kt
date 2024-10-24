package edu.uw.ischool.lgoing7.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextAmount: EditText
    private lateinit var buttonCalculateTip: Button
    private lateinit var spinnerTipPercentage: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextAmount = findViewById(R.id.editTextAmount)
        buttonCalculateTip = findViewById(R.id.buttonCalculateTip)
        spinnerTipPercentage = findViewById(R.id.spinnerTipPercentage)

        // setup dropdown for tip percentages
        val percentages = arrayOf("10%", "15%", "18%", "20%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, percentages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipPercentage.adapter = adapter

        // format input and enable button
        editTextAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val amount = s.toString().toDoubleOrNull()
                buttonCalculateTip.isEnabled = amount != null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // calc tip on button click
        buttonCalculateTip.setOnClickListener {
            val amount = editTextAmount.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val selectedPercentage = spinnerTipPercentage.selectedItem.toString().removeSuffix("%").toDouble()
            val tip = amount * (selectedPercentage / 100)
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            Toast.makeText(this, "Tip Amount: $formattedTip", Toast.LENGTH_LONG).show()
        }
    }
}