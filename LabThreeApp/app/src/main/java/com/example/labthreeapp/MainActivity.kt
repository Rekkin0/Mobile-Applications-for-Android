package com.example.labthreeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navHostFragment: Fragment
    private lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        setTheme(getMyTheme())
        getFontSize()?.let { theme.applyStyle(it, false) }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!
        navController = (navHostFragment as NavHostFragment).navController
        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.selectedItemId = R.id.fragmentCenter
/*        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragmentLeft -> navController.navigate(R.id.action_global_to_fragmentLeft)
                R.id.fragmentCenter -> navController.navigate(R.id.action_global_to_fragmentCenter)
                R.id.fragmentRight -> navController.navigate(R.id.action_global_to_fragmentRight)
                else -> return@setOnItemSelectedListener false
            }
            true
        }*/
        val appBarConfig = AppBarConfiguration(setOf(R.id.fragmentLeft,
            R.id.fragmentCenter, R.id.fragmentRight))
        setupActionBarWithNavController(navController, appBarConfig)
        bottomNavigation.setupWithNavController(navController)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        if (menu is MenuBuilder)
            menu.setOptionalIconsVisible(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val editor = sharedPreferences.edit()
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        else if (item.itemId == R.id.itemDefaultFont)
            editor.putBoolean("reset_font_size", true)
        else {
            editor.putInt("theme_choice",
                when (item.itemId) {
                    R.id.itemTheme1 -> R.style.Theme1_LabThreeApp
                    R.id.itemTheme2 -> R.style.Theme2_LabThreeApp
                    R.id.itemTheme3 -> R.style.Theme3_LabThreeApp
                    else -> R.style.Base_Theme_LabThreeApp
                }
            )
        }
        editor.apply()
        recreate()
        return super.onOptionsItemSelected(item)
    }

    private fun getMyTheme(): Int {
        return sharedPreferences.getInt("theme_choice", R.style.Base_Theme_LabThreeApp)
    }

    private fun getFontSize(): Int? {
        return if (sharedPreferences.getBoolean("reset_font_size", true))
            null
        else
            sharedPreferences.getInt("font_size", R.style.FontTheme16)
    }

    override fun onBackPressed() {
        val extraText = findViewById<EditText>(R.id.editText).text
        sharedPreferences
            .edit()
            .putString("extra_text", extraText.toString())
            .apply()
        super.onBackPressed()
    }

/*    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.itemNavigationCenter)
            super.onBackPressed()
        else
            bottomNavigation.selectedItemId = R.id.itemNavigationCenter
    }*/
}