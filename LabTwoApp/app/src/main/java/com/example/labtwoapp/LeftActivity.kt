package com.example.labtwoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Button

class LeftActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        when(v) {
            findViewById<Button>(R.id.buttonFontSize) -> menuInflater.inflate(R.menu.context_fontsize, menu)
            findViewById<Button>(R.id.buttonFontType) -> menuInflater.inflate(R.menu.context_fonttype, menu)
            else -> super.onCreateContextMenu(menu, v, menuInfo)
        }
    }
}