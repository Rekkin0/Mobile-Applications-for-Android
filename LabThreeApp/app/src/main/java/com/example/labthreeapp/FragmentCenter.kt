package com.example.labthreeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCenter.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentCenter : Fragment(), RadioGroup.OnCheckedChangeListener {
    private lateinit var fragment1: Fragment1
    private lateinit var fragment2: Fragment2
    private lateinit var transaction: FragmentTransaction

    private val TAG_FRAGMENT_1 = "Fragment1"
    private val TAG_FRAGMENT_2 = "Fragment2"

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        if (savedInstanceState == null) {
            fragment1 = Fragment1.newInstance()
            fragment2 = Fragment2.newInstance()

            transaction = childFragmentManager.beginTransaction()
            transaction.add(R.id.frameLayout, fragment1, TAG_FRAGMENT_1)
            transaction.detach(fragment1)
            transaction.add(R.id.frameLayout, fragment2, TAG_FRAGMENT_2)
            transaction.detach(fragment2)
            transaction.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_center, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            fragment1 = childFragmentManager.findFragmentByTag(TAG_FRAGMENT_1) as Fragment1
            fragment2 = childFragmentManager.findFragmentByTag(TAG_FRAGMENT_2) as Fragment2
        }
        requireActivity()
            .findViewById<RadioGroup>(R.id.radioGroup)
            .setOnCheckedChangeListener(this)
        childFragmentManager.setFragmentResultListener("valueFromChild", viewLifecycleOwner)
        { key, bundle ->
            val result = bundle.getString(key)
            requireActivity().findViewById<TextView>(R.id.textViewResult).text = result
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentCenter.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentCenter().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        transaction = childFragmentManager.beginTransaction()
        when (checkedId) {
            R.id.radioButton1 -> {
                transaction.detach(fragment2)
                transaction.attach(fragment1)
            }
            R.id.radioButton2 -> {
                transaction.detach(fragment1)
                transaction.attach(fragment2)
            }
        }
        transaction.commit()
    }
}