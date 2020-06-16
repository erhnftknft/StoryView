package com.erhn.ftknft.storyview.storyview

import android.view.View
import androidx.annotation.LayoutRes
import com.erhn.ftknft.storyview.storyview.adapter.SnapAdapter

interface IStoryView {

    fun setSnapAdapter(adapter: SnapAdapter)

    fun start()

    fun pause()

    fun resume()

    fun onEnd(action: () -> Unit)
}