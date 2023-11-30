package com.example.labfourapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.labfourapp.databinding.FragmentItemViewBinding

class ItemViewFragment : Fragment() {
    private val args: ItemViewFragmentArgs by navArgs()
    private val viewModel: ListViewModel by viewModels({ requireParentFragment() })
    private var editingBackPressedCallback: OnBackPressedCallback? = null
    private lateinit var binding: FragmentItemViewBinding
    private lateinit var item: ListItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = viewModel.item
        setEditable(args.editable)
    }

    private fun setEditable(editable: Boolean) {
        binding.editTextName.isEnabled = editable
        binding.editTextTitle.isEnabled = editable
        binding.ratingBar.isEnabled = editable
        binding.radioButton1.isEnabled = editable
        binding.radioButton2.isEnabled = editable
        binding.radioButton3.isEnabled = editable
        if (editable) setEditingMode() else setViewingMode()
    }

    private fun setEditingMode() {
        binding.buttonAction.text = getString(R.string.itemview_button_save)
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> binding.imageView.setImageResource(R.drawable.ic_noxus)
                R.id.radioButton2 -> binding.imageView.setImageResource(R.drawable.ic_demacia)
                R.id.radioButton3 -> binding.imageView.setImageResource(R.drawable.ic_shurima)
            }
        }
        binding.buttonAction.setOnClickListener { _ ->
            item.name = binding.editTextName.text.toString()
            item.title = binding.editTextTitle.text.toString()
            item.rating = binding.ratingBar.rating
            item.region = when {
                binding.radioButton1.isChecked -> 1
                binding.radioButton2.isChecked -> 2
                binding.radioButton3.isChecked -> 3
                else -> null
            }
            setEditable(false)
        }
        editingBackPressedCallback =
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                setEditable(false)
                isEnabled = false
            }
    }

    private fun setViewingMode() {
        editingBackPressedCallback?.isEnabled = false
        binding.buttonAction.text = getString(R.string.itemview_button_edit)
        binding.editTextName.setText(item.name)
        binding.editTextTitle.setText(item.title)
        binding.ratingBar.rating = item.rating
        when (item.region) {
            1 -> {
                binding.radioButton1.isChecked = true
                binding.imageView.setImageResource(R.drawable.ic_noxus)
            }
            2 -> {
                binding.radioButton2.isChecked = true
                binding.imageView.setImageResource(R.drawable.ic_demacia)
            }
            3 -> {
                binding.radioButton3.isChecked = true
                binding.imageView.setImageResource(R.drawable.ic_shurima)
            }
        }
        binding.buttonAction.setOnClickListener { _ ->
            setEditable(true)
        }

    }
}
