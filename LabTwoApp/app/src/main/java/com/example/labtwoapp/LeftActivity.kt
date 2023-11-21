package com.example.labtwoapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.graphics.Typeface.NORMAL
import android.graphics.Typeface.BOLD
import android.graphics.Typeface.ITALIC
import android.graphics.Typeface.BOLD_ITALIC
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Stack

class LeftActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var buttonFontSize: Button
    private lateinit var buttonFontStyle: Button
    private var fontSize = 20F
    private var isBoldChecked = false
    private var isItalicChecked = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        applyTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left)

        setSupportActionBar(findViewById(R.id.toolbar))

        bottomNavigation = findViewById(R.id.bottomNavigationView)
        bottomNavigation.selectedItemId = R.id.itemNavigationLeft
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemNavigationLeft -> {
                    true
                }
                R.id.itemNavigationMain -> {
                    onBackPressedDispatcher.onBackPressed()
                    true
                }
                R.id.itemNavigationRight -> {
                    startActivity(Intent(this, RightActivity::class.java))
                    true
                }
                else -> false
            }
        }

        buttonFontSize = findViewById(R.id.buttonFontSize)
        buttonFontStyle = findViewById(R.id.buttonFontStyle)
        buttonFontStyle.setTypeface(null, sharedPreferences.getInt("font_style", NORMAL))

        registerForContextMenu(buttonFontSize)
        registerForContextMenu(buttonFontStyle)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }

        fontSize = sharedPreferences.getFloat("font_size", 20F)
        isBoldChecked = sharedPreferences.getBoolean("is_bold_checked", false)
        isItalicChecked = sharedPreferences.getBoolean("is_italic_checked", false)
    }

    override fun onRestart() {
        recreate()
        bottomNavigation.selectedItemId = R.id.itemNavigationLeft
        super.onRestart()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when (v) {
            buttonFontSize -> {
                menu?.setHeaderTitle(getString(R.string.choose_font_size))
                menuInflater.inflate(R.menu.context_fontsize, menu)
            }
            buttonFontStyle -> {
                menu?.setHeaderTitle(getString(R.string.choose_font_style))
                menuInflater.inflate(R.menu.context_fontstyle, menu)
            }
        }

        val itemFontStyleBold: MenuItem? = menu?.findItem(R.id.itemFontStyleBold)
        val itemFontStyleItalic: MenuItem? = menu?.findItem(R.id.itemFontStyleItalic)

        itemFontStyleBold?.isChecked = isBoldChecked
        itemFontStyleItalic?.isChecked = isItalicChecked

        itemFontStyleBold?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
        itemFontStyleItalic?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)

        itemFontStyleBold?.actionView = View(applicationContext)
        itemFontStyleItalic?.actionView = View(applicationContext)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemTextSize16 -> fontSize = 16F
            R.id.itemTextSize20 -> fontSize = 20F
            R.id.itemTextSize22 -> fontSize = 22F
            R.id.itemTextSize24 -> fontSize = 24F
            R.id.itemFontStyleBold -> {
                isBoldChecked = !item.isChecked
                item.isChecked = isBoldChecked
            }
            R.id.itemFontStyleItalic -> {
                isItalicChecked = !item.isChecked
                item.isChecked = isItalicChecked
            }
            else -> super.onContextItemSelected(item)
        }
        saveTextPreferences()
        recreate()
        return true
    }

    private fun saveTextPreferences() {
        val editor = sharedPreferences.edit()
        editor
            .putFloat("font_size", fontSize)
            .putBoolean("is_bold_checked", isBoldChecked)
            .putBoolean("is_italic_checked", isItalicChecked)
        if (isBoldChecked && isItalicChecked)
            editor.putInt("font_style", BOLD_ITALIC)
        else if (isBoldChecked)
            editor.putInt("font_style", BOLD)
        else if (isItalicChecked)
            editor.putInt("font_style", ITALIC)
        else
            editor.putInt("font_style", NORMAL)
        editor.apply()
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