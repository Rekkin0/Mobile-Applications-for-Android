package com.example.labsixapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.labsixapp.databinding.FragmentItemViewBinding

class ItemViewFragment : Fragment() {
    private lateinit var binding: FragmentItemViewBinding
    private lateinit var item: DBListItem
    private val listViewModel: ListViewModel by activityViewModels { ListViewModel.Factory }

    override fun onResume() {
        super.onResume()
        displayData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        arguments?.let {
            val itemId = it.getInt("item_id")
            item = listViewModel.getItemById(itemId)
        }
        disableInputs()
        displayData()
        binding.buttonModify.setOnClickListener {
            val direction =
                ItemViewFragmentDirections.actionItemViewFragmentToItemModifyFragment(item.id)
            requireActivity().findNavController(R.id.fragmentContainerView)
                .navigate(direction)
        }
    }

    private fun disableInputs() {
        binding.ratingBar.isEnabled = false
        binding.radioButton1.isEnabled = false
        binding.radioButton2.isEnabled = false
        binding.radioButton3.isEnabled = false
    }

    private fun displayData() {
        binding.textViewName.text = item.name
        binding.textViewTitle.text = item.title
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
}
