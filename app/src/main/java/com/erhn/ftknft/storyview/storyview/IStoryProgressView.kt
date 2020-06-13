package com.erhn.ftknft.storyview.storyview

import androidx.annotation.ColorInt

interface IStoryProgressView {

    fun setFrontColor(@ColorInt color: Int)

    fun setBackColor(@ColorInt color: Int)

    fun currentProgress(): Int

    fun start()

    fun stop()

    fun pause()

    fun resume()

}