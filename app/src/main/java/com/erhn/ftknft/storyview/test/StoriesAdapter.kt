package com.erhn.ftknft.storyview.test

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erhn.ftknft.storyview.StoryFragment

class StoriesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = StoryFragment()

}