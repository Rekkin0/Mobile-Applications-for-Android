package com.example.laboneapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val editPhone: EditText = findViewById(R.id.editPhone)

        val buttonDial: Button = findViewById(R.id.buttonDial)
        buttonDial.setOnClickListener { _ ->
            val phoneNumber = editPhone.text.toString()
            runDial(phoneNumber)
        }

        val editMessage: EditText = findViewById(R.id.editMessage)
        val buttonSms: Button = findViewById(R.id.buttonSms)
        buttonSms.setOnClickListener { _ ->
            val phoneNumber = editPhone.text.toString()
            val message = editMessage.text.toString()
            runSms(phoneNumber, message)
        }

    }

    private fun runDial(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun runSms(phoneNumber: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", message)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}