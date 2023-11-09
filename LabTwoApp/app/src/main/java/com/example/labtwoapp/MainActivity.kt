package com.example.labtwoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        applyTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val buttonTheme1: Button = findViewById(R.id.buttonTheme1)
        buttonTheme1.setOnClickListener { _ ->
            saveThemePreferences(1)
            recreate()
        }

        val buttonTheme2: Button = findViewById(R.id.buttonTheme2)
        buttonTheme2.setOnClickListener { _ ->
            saveThemePreferences(2)
            recreate()
        }

        val buttonTheme3: Button = findViewById(R.id.buttonTheme3)
        buttonTheme3.setOnClickListener { _ ->
            saveThemePreferences(3)
            recreate()
        }

        val buttonLeft: Button = findViewById(R.id.buttonLeft)
        buttonLeft.setOnClickListener { _ ->
            val myIntent = Intent(this, LeftActivity::class.java)
            startActivity(myIntent)
        }

        val buttonRight: Button = findViewById(R.id.buttonRight)
        buttonRight.setOnClickListener { _ ->
            val myIntent = Intent(this, RightActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var themeId = 0
        when (item.itemId) {
            R.id.itemDefaultTheme -> themeId = 0
            R.id.itemTheme1 -> themeId = 1
            R.id.itemTheme2 -> themeId = 2
            R.id.itemTheme3 -> themeId = 3
            else -> super.onOptionsItemSelected(item)
        }
        saveThemePreferences(themeId)
        recreate()
        return true
    }

    private fun saveThemePreferences(themeId: Int) {
        sharedPreferences.edit()
            .putInt("theme_selected", themeId)
            .apply()
    }

    private fun applyTheme() {
        when (sharedPreferences.getInt("theme_selected", 0)) {
            1 -> setTheme(R.style.Theme1_LabTwoApp)
            2 -> setTheme(R.style.Theme2_LabTwoApp)
            3 -> setTheme(R.style.Theme3_LabTwoApp)
            else -> setTheme(R.style.Base_Theme_LabTwoApp)
        }
    }
}