package com.example.labthreeapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import java.util.Calendar

class FragmentRight : Fragment() {
    private lateinit var currentActivity: FragmentActivity
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textColor: TextView
    private lateinit var textDate: TextView
    private lateinit var checkboxView: View
    private var myActionMode: ActionMode? = null
    private var toastText = ""

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_right, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textColor = currentActivity.findViewById(R.id.textColor)
        setPreferredColor()

        val actionModeCallback = object: ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                currentActivity.menuInflater.inflate(R.menu.context_actionmenu, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                sharedPreferences
                    .edit()
                    .putInt("color_choice",
                        when (item?.itemId) {
                            R.id.itemColor1 -> currentActivity.getColor(R.color.theme1_seed)
                            R.id.itemColor2 -> currentActivity.getColor(R.color.theme2_seed)
                            R.id.itemColor3 -> currentActivity.getColor(R.color.theme3_seed)
                            else -> currentActivity.getColor(R.color.theme1_seed)
                        }
                    ).apply()
                refreshFragment()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                myActionMode = null
            }
        }

        textColor.setOnLongClickListener { _ ->
            if (myActionMode != null)
                return@setOnLongClickListener false
            myActionMode = (currentActivity as AppCompatActivity).startSupportActionMode(actionModeCallback)
            true
        }

        textDate = currentActivity.findViewById(R.id.textDate)
        textDate.text = sharedPreferences.getString("birth_date", "dd-mm-yyyy")
        textDate.setOnClickListener { _ ->
            val calendar = Calendar.getInstance()
            val dateDialog = DatePickerDialog(
                currentActivity, { _, year, monthOfYear, dayOfMonth ->
                    textDate.text = "${dayOfMonth}-${monthOfYear+1}-${year}"
                    sharedPreferences
                        .edit()
                        .putString("birth_date", textDate.text as String)
                        .apply()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dateDialog.show()
        }

        val inflater = LayoutInflater.from(currentActivity)
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

        val buttonBack: Button = currentActivity.findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener { _ ->
            val builder = AlertDialog.Builder(currentActivity)
            builder.setView(checkboxView)
            builder
                .setTitle(getString(R.string.going_back))
                .setMessage(getString(R.string.absolutely_certain))
                .setPositiveButton("Ok") { _, _ ->
                    currentActivity.findNavController(R.id.fragmentContainerView).popBackStack()
                    if (toastText == "")
                        toastText = getString(R.string.nothing_checked)
                    val toast = Toast.makeText(currentActivity, toastText, Toast.LENGTH_SHORT)
                    toast.show()
                }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
            builder.show()
        }
    }

    private fun setPreferredColor() {
        textColor.setBackgroundColor(sharedPreferences.getInt("color_choice",
            currentActivity.getColor(R.color.theme1_seed)
        ))
    }

    private fun refreshFragment() {
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()
    }
}