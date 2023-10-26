package com.example.laboneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
//            myIntent.putExtra("name", prevName)
//            myIntent.putExtra("email", prevEmail)
//            myIntent.putExtra("phone", prevPhone)
//            myIntent.putExtra("nickname", prevNickname)
            getNames.launch(myIntent)
        }

        val button3Activity: Button = findViewById(R.id.button3Activity)
        button3Activity.setOnClickListener { _ ->
            val myIntent = Intent(this, ThirdActivity::class.java)
            startActivity(myIntent)
//            getRating.launch(myIntent)
        }
    }

//    override fun onRestart() {
//        super.onRestart()
//        val toast: Toast = Toast.makeText(this,
//                                          "Back to main activity",
//                                          Toast.LENGTH_SHORT)
//        toast.show()
//    }

    private var prevName: String? = "abc xyz"
    private var prevEmail: String? = "abc@xyz.pl"
    private var prevPhone: String? = "71-32-45-09"
    private var prevNickname: String? = "abc"

    private val getNames = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            return@registerForActivityResult
        }
        val bundle: Bundle? = result.data?.extras

        val name = bundle?.getString("name", prevName)
        if (name != null) {
            val textName: TextView = findViewById(R.id.textName)
            textName.text = name
            prevName = name
        }
        val email = bundle?.getString("email", prevEmail)
        if (email != null) {
            prevEmail = email
        }
        val phone = bundle?.getString("phone", prevPhone)
        if (phone != null) {
            prevPhone = phone
        }
        val nickname = bundle?.getString("nickname", prevNickname)
        if (nickname != null) {
            val textNickname: TextView = findViewById(R.id.textNickname)
            textNickname.text = nickname
            prevNickname = nickname
        }
    }

    private val getRating = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if (result.resultCode != RESULT_OK) {
            return@registerForActivityResult
        }
        val intent: Intent? = result.data
        val rating: Float? = intent?.getFloatExtra("rating", 0.0F)
        println("rating $rating")
    }
}