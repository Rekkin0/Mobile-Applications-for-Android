package com.example.laboneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.activity.OnBackPressedCallback

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                sendDataBack()
            }
        })

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                sendDataBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sendDataBack() {
        val rating = findViewById<RatingBar>(R.id.ratingBar).rating

        val myIntent = Intent()
        myIntent.putExtra("rating", rating)

        setResult(RESULT_OK, myIntent)
        finish()
    }
}