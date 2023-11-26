package com.example.labthreeapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity

class FragmentExtra : Fragment() {
    private lateinit var currentActivity: FragmentActivity

    companion object {
        fun newInstance() = FragmentExtra().apply {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this.requireActivity()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val extraText = currentActivity.findViewById<EditText>(R.id.editText)
        extraText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                parentFragmentManager.setFragmentResult("textFromFragment",
                    bundleOf("extra_text" to extraText.text.toString()))
            }
        })
    }
}