package com.example.labtwoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        applyTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val leftActivityIntent = Intent(this, LeftActivity::class.java)
        val rightActivityIntent = Intent(this, RightActivity::class.java)

        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.selectedItemId = R.id.itemNavigationMain
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemNavigationLeft -> {
                    startActivity(leftActivityIntent)
                    true
                }
                R.id.itemNavigationMain -> {
                    true
                }
                R.id.itemNavigationRight -> {
                    startActivity(rightActivityIntent)
                    true
                }
                else -> false
            }
        }

        val viewGroup =
            ((findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup)!!
        //setTextPreferences(viewGroup)

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
        buttonLeft.setOnClickListener { _ -> startActivity(leftActivityIntent) }

        val buttonRight: Button = findViewById(R.id.buttonRight)
        buttonRight.setOnClickListener { _ -> startActivity(rightActivityIntent) }
    }

    override fun onRestart() {
        recreate()
        bottomNavigation.selectedItemId = R.id.itemNavigationMain
        super.onRestart()
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (menu is MenuBuilder)
            menu.setOptionalIconsVisible(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var themeId = sharedPreferences.getInt("theme_selected", 0)
        when (item.itemId) {
            R.id.itemDefaultTheme -> themeId = 0
            R.id.itemTheme1 -> themeId = 1
            R.id.itemTheme2 -> themeId = 2
            R.id.itemTheme3 -> themeId = 3
            R.id.itemDefaultFont -> themeId = 4
            else -> {}
        }
        saveThemePreferences(themeId)
        recreate()
        return true
    }

    private fun saveThemePreferences(themeId: Int) {
        val editor = sharedPreferences.edit()
        if (themeId != 4)
            editor.putInt("theme_selected", themeId)
        else
            editor.putFloat("font_size", 0F)
        editor.apply()
    }

    private fun applyTheme() {
        when (sharedPreferences.getInt("theme_selected", 0)) {
            1 -> setTheme(R.style.Theme1_LabTwoApp)
            2 -> setTheme(R.style.Theme2_LabTwoApp)
            3 -> setTheme(R.style.Theme3_LabTwoApp)
            else -> setTheme(R.style.Base_Theme_LabTwoApp)
        }
        when (sharedPreferences.getFloat("font_size", 16F)) {
            0F -> {}
            16F -> theme.applyStyle(R.style.FontTheme16, false)
            20F -> theme.applyStyle(R.style.FontTheme20, false)
            22F -> theme.applyStyle(R.style.FontTheme22, false)
            24F -> theme.applyStyle(R.style.FontTheme24, false)
        }

    }
}