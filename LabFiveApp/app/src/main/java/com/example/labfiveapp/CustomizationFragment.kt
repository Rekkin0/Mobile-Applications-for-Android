package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.labfiveapp.R
import com.example.labfiveapp.databinding.FragmentCustomizationBinding

class CustomizationFragment : Fragment() {
    private lateinit var binding: FragmentCustomizationBinding
    private val resultBundle = Bundle()

    companion object {
        fun newInstance() = CustomizationFragment().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomizationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioButton1.isChecked = true
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> {
                    resultBundle.putInt("image", R.drawable.ic_doggo)
                }
                R.id.radioButton2 -> {
                    resultBundle.putInt("image", R.drawable.ic_catto)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        parentFragment?.setFragmentResult("customizationData", resultBundle)
    }
}