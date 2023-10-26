package com.example.laboneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            onBackPressed()
        }
    }
}