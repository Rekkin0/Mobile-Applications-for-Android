package com.example.labtwoapp

import android.annotation.SuppressLint
import android.content.Context
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
import android.widget.Button

class LeftActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
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

        buttonFontSize = findViewById(R.id.buttonFontSize)
        buttonFontStyle = findViewById(R.id.buttonFontStyle)

        registerForContextMenu(buttonFontSize)
        registerForContextMenu(buttonFontStyle)

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }

        fontSize = sharedPreferences.getFloat("font_size", 20F)
        isBoldChecked = sharedPreferences.getBoolean("is_bold_checked", false)
        isItalicChecked = sharedPreferences.getBoolean("is_italic_checked", false)
        setTextProperties()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when (v) {
            buttonFontSize -> {
                menu?.setHeaderTitle("Choose a font size")
                menuInflater.inflate(R.menu.context_fontsize, menu)
            }
            buttonFontStyle -> {
                menu?.setHeaderTitle("Select font styles")
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
        setTextProperties()
        saveTextPreferences()
        return false
    }

    private fun saveTextPreferences() {
        sharedPreferences.edit()
            .putFloat("font_size", fontSize)
            .putBoolean("is_bold_checked", isBoldChecked)
            .putBoolean("is_italic_checked", isItalicChecked)
            .apply()
    }

    private fun setTextProperties() {
        buttonFontSize.setTextSize(COMPLEX_UNIT_SP, fontSize)
        if (isBoldChecked && isItalicChecked)
            buttonFontStyle.setTypeface(null, BOLD_ITALIC)
        else if (isBoldChecked)
            buttonFontStyle.setTypeface(null, BOLD)
        else if (isItalicChecked)
            buttonFontStyle.setTypeface(null, ITALIC)
        else
            buttonFontStyle.setTypeface(null, NORMAL)
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