package com.example.labthreeapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

class FragmentCenter : Fragment(), RadioGroup.OnCheckedChangeListener {
    private lateinit var currentActivity: FragmentActivity
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var transaction: FragmentTransaction
    private lateinit var fragment1: Fragment1
    private lateinit var fragment2: Fragment2

    companion object {
        private const val TAG_FRAGMENT_1 = "Fragment1"
        private const val TAG_FRAGMENT_2 = "Fragment2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this.requireActivity()
        sharedPreferences = currentActivity
            .getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            fragment1 = Fragment1.newInstance()
            fragment2 = Fragment2.newInstance()

            transaction = childFragmentManager.beginTransaction()
            transaction.add(R.id.frameLayout, fragment1, TAG_FRAGMENT_1)
            transaction.detach(fragment1)
            transaction.add(R.id.frameLayout, fragment2, TAG_FRAGMENT_2)
            transaction.detach(fragment2)
            transaction.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_center, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            fragment1 = childFragmentManager.findFragmentByTag(TAG_FRAGMENT_1) as Fragment1
            fragment2 = childFragmentManager.findFragmentByTag(TAG_FRAGMENT_2) as Fragment2
        }
        currentActivity
            .findViewById<RadioGroup>(R.id.radioGroup)
            .setOnCheckedChangeListener(this)
        childFragmentManager.setFragmentResultListener("valueFromChild", viewLifecycleOwner)
        { key, bundle ->
            val result = bundle.getString(key)
            currentActivity.findViewById<TextView>(R.id.textViewResult).text = result
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        transaction = childFragmentManager.beginTransaction()
        when (checkedId) {
            R.id.radioButton1 -> {
                transaction.detach(fragment2)
                transaction.attach(fragment1)
            }
            R.id.radioButton2 -> {
                transaction.detach(fragment1)
                transaction.attach(fragment2)
            }
        }
        transaction.commit()
    }
}

