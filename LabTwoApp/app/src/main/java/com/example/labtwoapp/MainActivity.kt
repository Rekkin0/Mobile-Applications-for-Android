package com.example.labtwoapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val buttonTheme1: Button = findViewById(R.id.buttonTheme1)
        buttonTheme1.setOnClickListener { _ ->
            setPreferences(1)
            recreate()
        }

        val buttonTheme2: Button = findViewById(R.id.buttonTheme2)
        buttonTheme2.setOnClickListener { _ ->
            setPreferences(2)
            recreate()
        }

        val buttonTheme3: Button = findViewById(R.id.buttonTheme3)
        buttonTheme3.setOnClickListener { _ ->
            setPreferences(3)
            recreate()
        }

        val buttonLeft: Button = findViewById(R.id.buttonLeft)
        buttonLeft.setOnClickListener { _ ->
            val myIntent = Intent(this, LeftActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var themeId = 0
        when(item.itemId) {
            R.id.itemDefaultTheme -> themeId = 0
            R.id.itemTheme1 -> themeId = 1
            R.id.itemTheme2 -> themeId = 2
            R.id.itemTheme3 -> themeId = 3
            else -> super.onOptionsItemSelected(item)
        }
        setPreferences(themeId)
        recreate()
        return true
    }

    private fun setPreferences(themeId: Int) {
        val data = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("theme_selected", themeId)
        editor.apply()
    }

    private fun applyTheme() {
        val data = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        when(data.getInt("theme_selected", 0)) {
            1 -> setTheme(R.style.Theme1_LabTwoApp)
            2 -> setTheme(R.style.Theme2_LabTwoApp)
            3 -> setTheme(R.style.Theme3_LabTwoApp)
            else -> setTheme(R.style.Base_Theme_LabTwoApp)
        }
    }
}