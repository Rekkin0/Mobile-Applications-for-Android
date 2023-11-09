package com.example.laboneapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

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

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val buttonSms: Button = findViewById(R.id.buttonSms)
        buttonSms.setOnClickListener { _ ->
            val phoneNumber = editPhone.text.toString()
            val message = { buttonId: Int ->
                when (buttonId) {
                    -1 -> ""
                    R.id.radioButton5 ->
                        findViewById<EditText>(R.id.editMessage).text.toString()
                    else -> findViewById<RadioButton>(buttonId).text.toString()
                }
            }
            runSms(phoneNumber, message(radioGroup.checkedRadioButtonId))
        }

        val editLocation: EditText = findViewById(R.id.editLocation)
        val buttonLocation: Button = findViewById(R.id.buttonLocation)
        buttonLocation.setOnClickListener { _ ->
            val location = editLocation.text.toString()
            runLocation(location)
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

    private fun runLocation(location: String) {
        val url = "https://www.google.com/maps/search/"
        //val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url+location))
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$location"))
        startActivity(intent)
    }
}