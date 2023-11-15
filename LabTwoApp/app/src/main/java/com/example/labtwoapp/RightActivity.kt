package com.example.labtwoapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.view.menu.MenuBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class RightActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigation: BottomNavigationView
    private var myActionMode: ActionMode? = null
    private lateinit var textColor: TextView
    private lateinit var textDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        applyTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_right)

        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.selectedItemId = R.id.itemNavigationRight
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemNavigationLeft -> {
                    startActivity(Intent(this, LeftActivity::class.java))
                    true
                }
                R.id.itemNavigationMain -> {
                    onBackPressedDispatcher.onBackPressed()
                    true
                }
                R.id.itemNavigationRight -> {
                    true
                }
                else -> false
            }
        }

        textColor = findViewById(R.id.textColor)
        textDate = findViewById(R.id.textDate)
        setPreferredColor()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val myActionModeCallback: ActionMode.Callback = object: ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.context_actionmenu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                var colorId = 0
                when (item?.itemId) {
                    R.id.itemColor1 -> colorId = 1
                    R.id.itemColor2 -> colorId = 2
                    R.id.itemColor3 -> colorId = 3
                }
                saveColorPreferences(colorId)
                setPreferredColor()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                myActionMode = null
            }
        }

        textColor.setOnLongClickListener { _ ->
            if (myActionMode != null)
                return@setOnLongClickListener false
            myActionMode = startSupportActionMode(myActionModeCallback)
            true
        }

        textDate.setOnClickListener { _ ->
            val calendar = Calendar.getInstance()
            val dateDialog = DatePickerDialog(
                this, { view, year, monthOfYear, dayOfMonth ->
                    textDate.text = "${dayOfMonth}-${monthOfYear+1}-${year}"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.show()
        }

        var buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Going back...")
                .setMessage("Are you absolutely certain?")
                .setPositiveButton("Ok") { dialog, which ->
                    onBackPressedDispatcher.onBackPressed()
                }.setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
            builder.create()
            builder.show()
        }
    }

    override fun onRestart() {
        recreate()
        bottomNavigation.selectedItemId = R.id.itemNavigationRight
        super.onRestart()
    }

    private fun saveColorPreferences(colorId: Int) {
        sharedPreferences.edit()
            .putInt("color_selected", colorId)
            .apply()
    }

    private fun setPreferredColor() {
        when (sharedPreferences.getInt("color_selected", 0)) {
            1 -> textColor.setBackgroundColor(getColor(R.color.theme1_seed))
            2 -> textColor.setBackgroundColor(getColor(R.color.theme2_seed))
            3 -> textColor.setBackgroundColor(getColor(R.color.theme3_seed))
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (menu is MenuBuilder)
            menu.setOptionalIconsVisible(true)
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