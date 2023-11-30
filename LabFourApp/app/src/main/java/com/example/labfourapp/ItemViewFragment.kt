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
    private lateinit var binding: FragmentItemViewBinding
    private lateinit var item: ListItem
    private var editingBackPressedCallback: OnBackPressedCallback? = null

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
        item = viewModel.getItem(args.itemId)
        setEditable(args.editable)
    }

    private fun setEditable(editable: Boolean) {
        binding.editTextTitle.isEnabled = editable
        binding.editTextDescription.isEnabled = editable
        binding.ratingBar.isEnabled = editable
        binding.radioButtonCategory1.isEnabled = editable
        binding.radioButtonCategory2.isEnabled = editable
        binding.radioButtonCategory3.isEnabled = editable
        if (editable) setEditingMode() else setViewingMode()
    }

    private fun setEditingMode() {
        binding.buttonAction.text = getString(R.string.itemview_button_save)
        binding.buttonAction.setOnClickListener { _ ->
            item.title = binding.editTextTitle.text.toString()
            item.description = binding.editTextDescription.text.toString()
            item.rating = binding.ratingBar.rating
            item.category = when {
                binding.radioButtonCategory1.isChecked -> 1
                binding.radioButtonCategory2.isChecked -> 2
                binding.radioButtonCategory3.isChecked -> 3
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
        binding.editTextTitle.setText(item.title)
        binding.editTextDescription.setText(item.description)
        binding.ratingBar.rating = item.rating
        when (item.category) {
            1 -> binding.radioButtonCategory1.isChecked = true
            2 -> binding.radioButtonCategory2.isChecked = true
            3 -> binding.radioButtonCategory3.isChecked = true
        }
        binding.buttonAction.setOnClickListener { _ ->
            setEditable(true)
        }

    }
}
