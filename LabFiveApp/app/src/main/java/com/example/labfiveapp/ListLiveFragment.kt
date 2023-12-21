package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.labfiveapp.databinding.FragmentListLiveBinding
import com.example.labfiveapp.databinding.ListItemBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class ListLiveFragment : Fragment() {
    private lateinit var binding: FragmentListLiveBinding
    private lateinit var myListAdapter: MyListAdapter
    private val listViewModel: ListViewModel by activityViewModels { ListViewModel.Factory }

    private val onItemAction = { item: DBListItem, action: Int ->
        when (action) {
            1 -> {
                val direction =
                    ListLiveFragmentDirections.actionListLiveFragmentToItemViewFragment(item.id)
                findNavController().navigate(direction)
            }
            2 -> {
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder
                    .setTitle(
                        "${getString(R.string.list_dialog_title)} \"${item.name}, ${item.title}\"?"
                    ).setMessage(getString(R.string.list_dialog_message))
                    .setPositiveButton(getString(R.string.list_dialog_button_positive))
                    { _, _ ->
                        listViewModel.deleteItemFlow(item)
                        //listViewModel.deleteItem(item))
                    }.setNegativeButton(getString(R.string.list_dialog_button_negative))
                    { dialog, _ ->
                        dialog.cancel()
                    }
                builder.create()
                builder.show()
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myListAdapter = MyListAdapter(onItemAction)
        /*myListAdapter.updateList(listViewModel.getAllItems())

        parentFragmentManager.setFragmentResultListener(
            "item_edited",
            this
        ) { _, _ ->
            myListAdapter.updateList(listViewModel.getAllItems())
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListLiveBinding.inflate(layoutInflater)
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
        binding.recyclerView.adapter = myListAdapter

        binding.floatingActionButton.setOnClickListener {
            val direction =
                ListLiveFragmentDirections.actionListLiveFragmentToItemModifyFragment(-1)
            findNavController().navigate(direction)
        }

        /*listViewModel.getAllItemsLive().observe(viewLifecycleOwner) {
            myListAdapter.updateList(it)
        }*/

        lifecycle.coroutineScope.launch {
            listViewModel.getAllItemsFlow().collect {
                myListAdapter.updateList(it)
            }
        }
    }

    val diffCallback = object: DiffUtil.ItemCallback<DBListItem>() {
        override fun areItemsTheSame(
            oldItem: DBListItem,
            newItem: DBListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DBListItem,
            newItem: DBListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private inner class MyListAdapter(
        private val onItemAction: (DBListItem, Int) -> Any
    ): ListAdapter<DBListItem, MyListAdapter.ViewHolder>(diffCallback) {
        private lateinit var itemBinding: ListItemBinding
        private lateinit var holder: ViewHolder

        fun updateList(newList: List<DBListItem>) {
            submitList(newList)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyListAdapter.ViewHolder {
            itemBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            holder = ViewHolder(itemBinding.root)
            return holder
        }

        override fun onBindViewHolder(holder: MyListAdapter.ViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(getItem(position))
            holder.itemView.setOnClickListener {
                onItemAction(item, 1)
            }
            holder.itemView.setOnLongClickListener {
                onItemAction(item, 2)
                true
            }
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
            }
        }
    }
}