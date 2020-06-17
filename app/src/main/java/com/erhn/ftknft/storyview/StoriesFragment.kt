package com.erhn.ftknft.storyview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhn.ftknft.storyview.test.StoriesAdapter
import kotlinx.android.synthetic.main.fragment_stories.*

class StoriesFragment : Fragment() {

    lateinit var storiesAdapter: StoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storiesAdapter = StoriesAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager.adapter = storiesAdapter
    }

    fun next() {
        val currentPage = pager.currentItem
        val count = storiesAdapter.itemCount
        if (currentPage < count - 1) {
            pager.setCurrentItem(currentPage + 1, true)
        }
    }


}