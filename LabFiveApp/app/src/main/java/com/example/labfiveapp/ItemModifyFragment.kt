package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.labfiveapp.databinding.FragmentItemModifyBinding

class ItemModifyFragment : Fragment() {
    private lateinit var binding: FragmentItemModifyBinding
    private lateinit var dataRepository: DataRepository
    private lateinit var item: DBListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataRepository = DataRepository(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemModifyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemId = it.getInt("item_id")
            if (itemId != -1) {
                item = dataRepository.getById(itemId)!!
            }
        }
        displayData()
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> binding.imageView.setImageResource(R.drawable.ic_noxus)
                R.id.radioButton2 -> binding.imageView.setImageResource(R.drawable.ic_demacia)
                R.id.radioButton3 -> binding.imageView.setImageResource(R.drawable.ic_shurima)
            }
        }
        binding.buttonSave.setOnClickListener { _ ->
            if (!::item.isInitialized) {
                item = createItem()
                dataRepository.insert(item)
            } else modifyItem()
            parentFragmentManager.setFragmentResult("item_edited", Bundle.EMPTY)
            parentFragmentManager.popBackStack()
        }
    }

    private fun displayData() {
        if (!::item.isInitialized) return
        binding.editTextName.setText(item.name)
        binding.editTextTitle.setText(item.title)
        binding.ratingBar.rating = item.rating
        when (item.region) {
            1 -> Pair(R.id.radioButton1, R.drawable.ic_noxus)
            2 -> Pair(R.id.radioButton2, R.drawable.ic_demacia)
            3 -> Pair(R.id.radioButton3, R.drawable.ic_shurima)
            else -> null
        }?.let {
            binding.radioGroup.check(it.first)
            binding.imageView.setImageResource(it.second)
        }
    }

    private fun createItem(): DBListItem {
        return DBListItem(
            name = binding.editTextName.text.toString(),
            title = binding.editTextTitle.text.toString(),
            rating = binding.ratingBar.rating,
            region = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.radioButton1 -> 1
                R.id.radioButton2 -> 2
                R.id.radioButton3 -> 3
                else -> null
            }
        )
    }

    private fun modifyItem() {
        item.name = binding.editTextName.text.toString()
        item.title = binding.editTextTitle.text.toString()
        item.rating = binding.ratingBar.rating
        item.region = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButton1 -> 1
            R.id.radioButton2 -> 2
            R.id.radioButton3 -> 3
            else -> null
        }
        dataRepository.update(item)
    }
}