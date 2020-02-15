package com.stnamco.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.stnamco.shared.Calculator
import com.stnamco.shared.Greeting

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.main_text).text = Greeting().greeting()
        findViewById<TextView>(R.id.calculator_result).text = "Result: ${Calculator.sum(2,5)}"
    }
}
