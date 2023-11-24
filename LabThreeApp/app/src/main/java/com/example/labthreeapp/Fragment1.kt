package com.example.labthreeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import androidx.core.os.bundleOf

class Fragment1 : Fragment() {
    companion object {
        fun newInstance() = Fragment1().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonSend).setOnClickListener { _ ->
            val ratingValue = view.findViewById<RatingBar>(R.id.ratingBar).rating
            parentFragmentManager.setFragmentResult("valueFromChild",
                bundleOf("valueFromChild" to ("${getString(R.string.rating)} = $ratingValue"))
            )
        }
    }
}