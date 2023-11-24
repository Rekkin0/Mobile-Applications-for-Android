package com.example.labtwoapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar
import java.util.Stack


class RightActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bottomNavigation: BottomNavigationView
    private var myActionMode: ActionMode? = null
    private lateinit var textColor: TextView
    private lateinit var textDate: TextView
    private var toastText = ""
    private lateinit var checkboxView: View

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
                this, { _, year, monthOfYear, dayOfMonth ->
                    textDate.text = "${dayOfMonth}-${monthOfYear+1}-${year}"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.show()
        }

        val inflater = LayoutInflater.from(this)
        checkboxView = inflater.inflate(R.layout.dialog_checkboxes, null)

        val checkbox1 = checkboxView.findViewById<CheckBox>(R.id.checkBox1)
        val checkbox2 = checkboxView.findViewById<CheckBox>(R.id.checkBox2)
        val checkbox3 = checkboxView.findViewById<CheckBox>(R.id.checkBox3)

        checkbox1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toastText += "${checkbox1.text}\n"
        }

        checkbox2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toastText += "${checkbox2.text}\n"
        }

        checkbox3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                toastText += "${checkbox3.text}\n"
        }

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            val builder = AlertDialog.Builder(this)
            builder.setView(checkboxView)
            builder
                .setTitle(getString(R.string.going_back))
                .setMessage(getString(R.string.absolutely_certain))
                .setPositiveButton("Ok") { _, _ ->
                    onBackPressedDispatcher.onBackPressed()
                    if (toastText == "")
                        toastText = getString(R.string.nothing_checked)
                    val toast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT)
                    toast.show()
                }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
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
            R.id.itemDefaultFont -> themeId = 4
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
        when (sharedPreferences.getFloat("font_size", 16F)) {
            16F -> theme.applyStyle(R.style.FontTheme16, false)
            20F -> theme.applyStyle(R.style.FontTheme20, false)
            22F -> theme.applyStyle(R.style.FontTheme22, false)
            24F -> theme.applyStyle(R.style.FontTheme24, false)
        }
    }
}