package com.example.laboneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1Activity: Button = findViewById(R.id.button1Activity)
        button1Activity.setOnClickListener { _ ->
            val myIntent = Intent(this, FirstActivity::class.java)
            startActivity(myIntent)
        }

        val button2Activity: Button = findViewById(R.id.button2Activity)
        button2Activity.setOnClickListener { _ ->
            val myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)
        }

        val button3Activity: Button = findViewById(R.id.button3Activity)
        button3Activity.setOnClickListener { _ ->
            val myIntent = Intent(this, ThirdActivity::class.java)
            startActivity(myIntent)
        }

        val getNameResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    val name = intent?.getStringExtra("name")

                    if (name != null) {
                        val textName: TextView = findViewById(R.id.textName)
                        textName.text = name
                    }
                }
            }
    }
}