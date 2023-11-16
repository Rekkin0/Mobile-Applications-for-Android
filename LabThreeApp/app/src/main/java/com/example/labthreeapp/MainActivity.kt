package com.example.labthreeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), StaticFragment.OnSelectListener {
    private lateinit var fragment1: Fragment1
    private lateinit var fragment2: Fragment2
    private lateinit var transaction: FragmentTransaction

    private val TAG_FRAGMENT_1 = "Fragment1"
    private val TAG_FRAGMENT_2 = "Fragment2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragment1 = Fragment1.newInstance()
            fragment2 = Fragment2.newInstance()

            transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.frameLayout, fragment1, TAG_FRAGMENT_1)
            transaction.detach(fragment1)
            transaction.add(R.id.frameLayout, fragment2, TAG_FRAGMENT_2)
            transaction.detach(fragment2)
            transaction.commit()
        } else {
            fragment1 = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_1) as Fragment1
            fragment2 = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_2) as Fragment2
        }
    }

    override fun onSelect(option: Int) {
        transaction = supportFragmentManager.beginTransaction()
        when (option) {
            1 -> {
                transaction.detach(fragment2)
                transaction.attach(fragment1)
            }
            2 -> {
                transaction.detach(fragment1)
                transaction.attach(fragment2)
            }
        }
        transaction.commit()
    }
}