package com.example.laboneapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            onBackPressed()
        }
    }
}