package com.example.labsixapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.labsixapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("homeEditorData") { _, bundle ->
            binding.textViewGreetings.text =
                bundle.getString("greetings", getString(R.string.home_greetings))
            binding.textViewAuthor.text =
                bundle.getString("author", getString(R.string.home_author))
        }
        setFragmentResultListener("swipeData") { _, bundle ->
            binding.imageView.setImageResource(bundle.getInt("image", R.drawable.ic_doggo))
        }
    }
}