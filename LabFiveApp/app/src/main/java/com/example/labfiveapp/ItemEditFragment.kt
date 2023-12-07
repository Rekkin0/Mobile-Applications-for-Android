package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.labfiveapp.R
import com.example.labfiveapp.databinding.FragmentItemEditBinding

class ItemEditFragment : Fragment() {
    private val viewModel: ListViewModel by viewModels({ requireParentFragment() })
    private lateinit var binding: FragmentItemEditBinding
    private lateinit var item: ListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = viewModel.item
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> binding.imageView.setImageResource(R.drawable.ic_noxus)
                R.id.radioButton2 -> binding.imageView.setImageResource(R.drawable.ic_demacia)
                R.id.radioButton3 -> binding.imageView.setImageResource(R.drawable.ic_shurima)
            }
        }
        binding.buttonSave.setOnClickListener { _ ->
            saveData()
            parentFragmentManager.popBackStack()
        }
    }

    private fun setData() {
        binding.editTextName.setText(item.name)
        binding.editTextTitle.setText(item.title)
        binding.ratingBar.rating = item.rating
        when(item.region) {
            1 -> Pair(R.id.radioButton1, R.drawable.ic_noxus)
            2 -> Pair(R.id.radioButton2, R.drawable.ic_demacia)
            3 -> Pair(R.id.radioButton3, R.drawable.ic_shurima)
            else -> null
        }?.let {
            binding.radioGroup.check(it.first)
            binding.imageView.setImageResource(it.second)
        }
    }

    private fun saveData() {
        var name = binding.editTextName.text.toString()
        if (name.isBlank()) name = "New champion"
        item.name = name

        var title = binding.editTextTitle.text.toString()
        if (title.isBlank()) title = "New title"
        item.title = title

        item.rating = binding.ratingBar.rating
        item.region = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButton1 -> 1
            R.id.radioButton2 -> 2
            R.id.radioButton3 -> 3
            else -> null
        }
    }
}