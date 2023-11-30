package com.example.labfourapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.example.labfourapp.databinding.FragmentHomeEditorBinding

class HomeEditorFragment : Fragment() {
    private lateinit var binding: FragmentHomeEditorBinding
    private val resultBundle = Bundle()

    companion object {
        fun newInstance() = HomeEditorFragment().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeEditorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextGreetings.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                resultBundle.putString(
                    "greetings",
                    if (s.isNullOrBlank()) null else s.toString()
                )
            }
        })
        binding.editTextAuthor.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                resultBundle.putString(
                    "author",
                    if (s.isNullOrBlank()) null else s.toString()
                )
            }
        })
    }

    override fun onStop() {
        super.onStop()
        parentFragment?.setFragmentResult("homeEditorData", resultBundle)
    }
}