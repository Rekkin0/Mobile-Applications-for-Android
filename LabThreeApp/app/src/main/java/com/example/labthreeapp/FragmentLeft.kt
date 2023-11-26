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
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController

class FragmentLeft : Fragment() {
    private lateinit var currentActivity: FragmentActivity
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var buttonFontSize: Button
    private lateinit var buttonFontStyle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this.requireActivity()
        sharedPreferences = currentActivity
            .getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonFontSize = currentActivity.findViewById(R.id.buttonFontSize)
        buttonFontStyle = currentActivity.findViewById(R.id.buttonFontStyle)
        buttonFontStyle.setTypeface(null, getFontStyle())

        registerForContextMenu(buttonFontSize)
        registerForContextMenu(buttonFontStyle)

        val buttonExtra: Button = currentActivity.findViewById(R.id.buttonExtra)
        buttonExtra.setOnClickListener { _ ->
            currentActivity.findNavController(R.id.fragmentContainerView)
                .navigate(R.id.action_fragmentLeft_to_fragmentExtra)
        }
        parentFragmentManager.setFragmentResultListener("textFromFragment", viewLifecycleOwner)
        { key, bundle ->
            val result = bundle.getString("extra_text")
            currentActivity.findViewById<TextView>(R.id.textExtra).text = result
        }

/*        val buttonBack: Button = currentActivity.findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            currentActivity.findNavController(R.id.fragmentContainerView).popBackStack()
        }*/
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

        itemFontStyleBold?.isChecked = sharedPreferences.getBoolean("is_bold_checked", false)
        itemFontStyleItalic?.isChecked = sharedPreferences.getBoolean("is_italic_checked", false)

        itemFontStyleBold?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
        itemFontStyleItalic?.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)

        itemFontStyleBold?.actionView = View(currentActivity.applicationContext)
        itemFontStyleItalic?.actionView = View(currentActivity.applicationContext)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val editor = sharedPreferences.edit()
        when (item.itemId) {
            R.id.itemFontSize16 -> { editor.putInt("font_size", R.style.FontTheme16); fontSizeChanged() }
            R.id.itemFontSize20 -> { editor.putInt("font_size", R.style.FontTheme20); fontSizeChanged() }
            R.id.itemFontSize24 -> { editor.putInt("font_size", R.style.FontTheme24); fontSizeChanged() }
            R.id.itemFontStyleBold -> {
                item.isChecked = !item.isChecked
                editor.putBoolean("is_bold_checked", item.isChecked)
            }
            R.id.itemFontStyleItalic -> {
                item.isChecked = !item.isChecked
                editor.putBoolean("is_italic_checked", item.isChecked)
            }
            else -> super.onContextItemSelected(item)
        }
        editor.apply()
        refreshFragment()
        return false
    }

    private fun fontSizeChanged() {
        sharedPreferences
            .edit()
            .putBoolean("reset_font_size", false)
            .apply()
        currentActivity.recreate()
    }

    private fun getFontStyle(): Int {
        val isBoldChecked = sharedPreferences.getBoolean("is_bold_checked", false)
        val isItalicChecked = sharedPreferences.getBoolean("is_italic_checked", false)
        return if (isBoldChecked && isItalicChecked)
            Typeface.BOLD_ITALIC
        else if (isBoldChecked)
            Typeface.BOLD
        else if (isItalicChecked)
            Typeface.ITALIC
        else
            Typeface.NORMAL
    }

    private fun refreshFragment() {
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()
    }
}