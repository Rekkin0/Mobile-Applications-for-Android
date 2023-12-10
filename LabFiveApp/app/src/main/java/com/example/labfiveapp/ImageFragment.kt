package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.labfiveapp.databinding.FragmentImageDogBinding

class ImageDogFragment : Fragment() {
    private lateinit var binding: FragmentImageDogBinding
    private var position: Int? = null

    companion object {
        fun newInstance(position: Int) = ImageDogFragment().apply {
            arguments = Bundle().apply {
                putInt("position", position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentImageDogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setImageResource(
            when (position) {
                0 -> R.drawable.ic_doggo
                1 -> R.drawable.ic_catto
                else -> R.drawable.ic_doggo
            }
        )
    }
}