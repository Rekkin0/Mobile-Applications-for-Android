package com.example.labfiveapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.labfiveapp.databinding.FragmentSwipeBinding
import com.google.android.material.tabs.TabLayoutMediator

class SwipeFragment : Fragment() {
    private lateinit var binding: FragmentSwipeBinding

    companion object {
        private const val PAGE_COUNT = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSwipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.swipe_image_dog_tab)
                1 -> getString(R.string.swipe_image_cat_tab)
                else -> getString(R.string.swipe_image_dog_tab)
            }
        }.attach()
    }

    private inner class ViewPagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = PAGE_COUNT

        override fun createFragment(position: Int): Fragment {
            return ImageFragment.newInstance(position)
        }
    }

    override fun onPause() {
        super.onPause()
        val bundle = Bundle()
        bundle.putInt("image",
            when (binding.viewPager.currentItem) {
                0 -> R.drawable.ic_doggo
                1 -> R.drawable.ic_catto
                else -> R.drawable.ic_doggo
            }
        )
        parentFragmentManager.setFragmentResult("swipeData", bundle)
    }
}