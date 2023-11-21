package com.example.labthreeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: Fragment
    private lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

/*    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.itemNavigationCenter)
            super.onBackPressed()
        else
            bottomNavigation.selectedItemId = R.id.itemNavigationCenter
    }*/
}