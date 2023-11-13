package com.example.labtwoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder

class RightActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var myActionMode: ActionMode? = null
    //private val textColor: TextView = findViewById(R.id.textColor)

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        applyTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_right)

        setSupportActionBar(findViewById(R.id.toolbar))

        val myActionModeCallback: ActionMode.Callback = object: ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.context_actionmenu, menu)
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                var colorId = 0
                when (item?.itemId) {
                    R.id.itemColor1 -> colorId = 1
                    R.id.itemColor2 -> colorId = 2
                    R.id.itemColor3 -> colorId = 3
                }
                //saveColorPreferences(colorId)
                //setPreferredColor()
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                myActionMode = null
            }
        }

        /*textColor.setOnLongClickListener { _ ->
            if (myActionMode != null)
                return@setOnLongClickListener false
            myActionMode = startActionMode(myActionModeCallback)
            true
        }*/
    }

    /*private fun saveColorPreferences(colorId: Int) {
        sharedPreferences.edit()
            .putInt("color_selected", colorId)
            .apply()
    }

    private fun setPreferredColor() {
        when (sharedPreferences.getInt("color_selected", 0)) {
            1 -> textColor.setBackgroundColor(R.color.theme1_seed)
            2 -> textColor.setBackgroundColor(R.color.theme2_seed)
            3 -> textColor.setBackgroundColor(R.color.theme3_seed)
        }
    }*/

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