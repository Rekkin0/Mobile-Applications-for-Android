package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labfiveapp.databinding.FragmentListBinding
import com.example.labfiveapp.databinding.ListItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var dataRepository: DataRepository
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataRepository = DataRepository(requireContext())
        recyclerViewAdapter = RecyclerViewAdapter(dataRepository.getAll()!!)

        parentFragmentManager.setFragmentResultListener(
            "item_edited",
            this
        ) { _, _ ->
            recyclerViewAdapter.updateList(dataRepository.getAll()!!)
        }
    }

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
        binding.recyclerView.adapter = recyclerViewAdapter

        binding.floatingActionButton.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToItemModifyFragment(-1)
            findNavController().navigate(action)
        }
    }

    private inner class RecyclerViewAdapter(
        private var itemList: MutableList<DBListItem>
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        private lateinit var itemBinding: ListItemBinding

        fun updateList(newList: MutableList<DBListItem>) {
            itemList = newList
            notifyDataSetChanged()
        }

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
            fun bind(item: DBListItem) {
                itemBinding.textViewName.text = item.name
                itemBinding.textViewTitle.text = item.title
                when (item.region) {
                    1 -> R.drawable.ic_noxus
                    2 -> R.drawable.ic_demacia
                    3 -> R.drawable.ic_shurima
                    else -> null
                }?.let { itemBinding.imageView.setImageResource(it) }
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
                    val action =
                        ListFragmentDirections.actionListFragmentToItemViewFragment(item.id)
                    findNavController().navigate(action)
                }

                itemView.setOnLongClickListener {
                    val builder = MaterialAlertDialogBuilder(requireContext())
                    builder
                        .setTitle(
                            "${getString(R.string.list_dialog_title)} \"${item.name}, ${item.title}\"?"
                        ).setMessage(getString(R.string.list_dialog_message))
                        .setPositiveButton(getString(R.string.list_dialog_button_positive))
                        { _, _ ->
                            if (dataRepository.delete(item)) {
                                updateList(dataRepository.getAll()!!)
                            }
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
}