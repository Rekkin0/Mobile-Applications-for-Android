package com.example.laboneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fun setDefault(extraName: String, editText: EditText) {
            editText.setText(intent.getStringExtra(extraName))
        }
//        setDefault("name", findViewById(R.id.editName))
//        setDefault("email", findViewById(R.id.editEmail))
//        setDefault("phone", findViewById(R.id.editPhone))
//        setDefault("nickname", findViewById(R.id.editNickname))

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
        val name = findViewById<EditText>(R.id.editName).text.toString()
        val email = findViewById<EditText>(R.id.editEmail).text.toString()
        val phone = findViewById<EditText>(R.id.editPhone).text.toString()
        val nickname = findViewById<EditText>(R.id.editNickname).text.toString()

        val bundle = Bundle()
        if (name.isNotEmpty()) {
            bundle.putString("name", name)
        }
        if (email.isNotEmpty()) {
            bundle.putString("email", email)
        }
        if (phone.isNotEmpty()) {
            bundle.putString("phone", phone)
        }
        if (nickname.isNotEmpty()) {
            bundle.putString("nickname", nickname)
        }

        val myIntent = Intent()
        myIntent.putExtras(bundle)

        setResult(RESULT_OK, myIntent)
        finish()
    }
}