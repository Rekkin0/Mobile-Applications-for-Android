package com.example.labthreeapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController

class FragmentLeft : Fragment() {
    private lateinit var currentActivity: FragmentActivity
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var buttonFontSize: Button
    private lateinit var buttonFontStyle: Button
    private var fontSize = 16F
    private var isBoldChecked = false
    private var isItalicChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this.requireActivity()
        sharedPreferences = requireActivity()
            .getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonFontSize = currentActivity.findViewById(R.id.buttonFontSize)
        buttonFontStyle = currentActivity.findViewById(R.id.buttonFontStyle)
        buttonFontStyle.setTypeface(null, sharedPreferences.getInt("font_style", Typeface.NORMAL))

        registerForContextMenu(buttonFontSize)
        registerForContextMenu(buttonFontStyle)

        isBoldChecked = sharedPreferences.getBoolean("is_bold_checked", false)
        isItalicChecked = sharedPreferences.getBoolean("is_italic_checked", false)

        applyTheme()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        when (v) {
            buttonFontSize -> {
                menu.setHeaderTitle(getString(R.string.choose_font_size))
                currentActivity.menuInflater.inflate(R.menu.context_fontsize, menu)
            }
            buttonFontStyle -> {
                menu.setHeaderTitle(getString(R.string.choose_font_style))
                currentActivity.menuInflater.inflate(R.menu.context_fontstyle, menu)
            }
        }
        val itemFontStyleBold: MenuItem? = menu.findItem(R.id.itemFontStyleBold)
        val itemFontStyleItalic: MenuItem? = menu.findItem(R.id.itemFontStyleItalic)

        itemFontStyleBold?.isChecked = isBoldChecked
        itemFontStyleItalic?.isChecked = isItalicChecked

        itemFontStyleBold?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
        itemFontStyleItalic?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)

        itemFontStyleBold?.actionView = View(currentActivity.applicationContext)
        itemFontStyleItalic?.actionView = View(currentActivity.applicationContext)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemTextSize16 -> fontSize = 16F
            R.id.itemTextSize20 -> fontSize = 20F
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
        refreshFragment()
        return false
    }

    private fun saveTextPreferences() {
        val editor = sharedPreferences.edit()
        editor
            .putFloat("font_size", fontSize)
            .putBoolean("is_bold_checked", isBoldChecked)
            .putBoolean("is_italic_checked", isItalicChecked)
        if (isBoldChecked && isItalicChecked)
            editor.putInt("font_style", Typeface.BOLD_ITALIC)
        else if (isBoldChecked)
            editor.putInt("font_style", Typeface.BOLD)
        else if (isItalicChecked)
            editor.putInt("font_style", Typeface.ITALIC)
        else
            editor.putInt("font_style", Typeface.NORMAL)
        editor.apply()
    }

    private fun applyTheme() {
        when (sharedPreferences.getInt("theme_choice", 0)) {
            1 -> currentActivity.setTheme(R.style.Theme1_LabThreeApp)
            2 -> currentActivity.setTheme(R.style.Theme2_LabThreeApp)
            3 -> currentActivity.setTheme(R.style.Theme3_LabThreeApp)
            else -> currentActivity.setTheme(R.style.Base_Theme_LabThreeApp)
        }
        when (sharedPreferences.getFloat("font_size", 16F)) {
            16F -> currentActivity.theme.applyStyle(R.style.FontTheme16, false)
            20F -> currentActivity.theme.applyStyle(R.style.FontTheme20, false)
            24F -> currentActivity.theme.applyStyle(R.style.FontTheme24, false)
            else -> {}
        }
    }

    private fun refreshFragment() {
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()
    }
}