package com.erhn.ftknft.storyview.storyview

import android.view.View
import androidx.annotation.LayoutRes
import com.erhn.ftknft.storyview.storyview.adapter.SnapAdapter

interface IStoryView {

    fun setSnapAdapter(adapter: SnapAdapter)

    fun start()

    fun pause()

    fun resume()

    fun cancel()

    fun nextSnap()

    fun prevSnap()

    fun setOnLeftSideClick(action: (currentPlayed: Int) -> Unit)

    fun setOnRightSideClick(action: (currentPlayed: Int) -> Unit)

    fun setOnLongPress(action: (longPressAction: LongPressAction) -> Unit)

    fun onEnd(action: () -> Unit)

    enum class LongPressAction {
        DOWN, UP
    }
}