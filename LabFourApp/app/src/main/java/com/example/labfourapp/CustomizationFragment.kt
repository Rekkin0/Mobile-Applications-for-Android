package com.example.labfourapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.labfourapp.databinding.FragmentCustomizationBinding

class CustomizationFragment : Fragment() {
    private lateinit var binding: FragmentCustomizationBinding

    companion object {
        fun newInstance() = CustomizationFragment().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomizationBinding.inflate(layoutInflater)
        return binding.root
    }
}