package com.example.labfourapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labfourapp.databinding.FragmentListBinding
import com.example.labfourapp.databinding.ListItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                context,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
        )

        viewModel.itemList.observe(viewLifecycleOwner) { items ->
            val recyclerViewAdapter = RecyclerViewAdapter(items) { item ->
                viewItem(item)
            }
            binding.recyclerView.adapter = recyclerViewAdapter
        }

        binding.floatingActionButton.setOnClickListener {
            val item = ListItem("New champion", "New title")
            viewModel.addItem(item)
            viewItem(item)
        }
    }

    private fun viewItem(item: ListItem) {
        viewModel.item = item
        val action =
            ListFragmentDirections.actionListFragmentToItemViewFragment()
        findNavController().navigate(action)
    }

    private inner class RecyclerViewAdapter(
        private val itemList: List<ListItem>,
        private val onItemClick: (ListItem) -> Unit
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        private lateinit var itemBinding: ListItemBinding

        override fun getItemCount(): Int = itemList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            itemBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(itemBinding.root)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(itemList[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: ListItem) {
                itemBinding.textViewName.text = item.name
                itemBinding.textViewTitle.text = item.title
                when (item.region) {
                    1 -> R.drawable.ic_noxus
                    2 -> R.drawable.ic_demacia
                    3 -> R.drawable.ic_shurima
                    else -> null
                }?.let {
                    itemBinding.imageView.setImageResource(
                        it
                    )
                }
                when (item.rating) {
                    in 0.0F..2.0F -> itemBinding.textViewRating.setBackgroundColor(
                        resources.getColor(R.color.red)
                    )
                    in 2.5F..3.5F -> itemBinding.textViewRating.setBackgroundColor(
                        resources.getColor(R.color.orange)
                    )
                    in 4.0F..4.5F -> itemBinding.textViewRating.setBackgroundColor(
                        resources.getColor(R.color.green)
                    )
                    5.0F -> itemBinding.textViewRating.setBackgroundColor(
                        resources.getColor(R.color.yellow)
                    )
                }
                itemBinding.textViewRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    onItemClick(item)
                }

                itemView.setOnLongClickListener {
                    val builder = MaterialAlertDialogBuilder(requireContext())
                    builder
                        .setTitle(
                            "${getString(R.string.list_dialog_title)} \"${item.name}, ${item.title}\"?"
                        ).setMessage(getString(R.string.list_dialog_message))
                        .setPositiveButton(getString(R.string.list_dialog_button_positive))
                        { _, _ ->
                            viewModel.removeItem(item)
                            refreshFragment()
                        }.setNegativeButton(getString(R.string.list_dialog_button_negative))
                        { dialog, _ ->
                            dialog.cancel()
                        }
                    builder.create()
                    builder.show()
                    true
                }
            }
        }
    }

    private fun refreshFragment() {
        parentFragmentManager.beginTransaction().detach(this).commit()
        parentFragmentManager.beginTransaction().attach(this).commit()
    }
}