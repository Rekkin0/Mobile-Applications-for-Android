package com.example.labsixapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.labsixapp.databinding.FragmentCameraBinding
import com.example.labsixapp.databinding.PhotoItemBinding

class CameraFragment : Fragment() {
    private lateinit var binding: FragmentCameraBinding
    private lateinit var photoGrid: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCameraBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoGrid = binding.photoGrid
        photoGrid.layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
    }

    private inner class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
        private val photoList = mutableListOf<PhotoItem>()
        private lateinit var photoBinding: PhotoItemBinding
        private lateinit var holder: ViewHolder

        override fun getItemCount() = photoList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            photoBinding = PhotoItemBinding.inflate(layoutInflater, parent, false)
            holder = ViewHolder(photoBinding.root)
            return holder

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(photoList[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(photoItem: PhotoItem) {
                photoBinding.photoImage.setImageResource(photoItem.imageResourceId)
            }
        }
    }
}