package com.erhn.ftknft.storyview.storyview.progress

import androidx.annotation.ColorInt
import java.util.concurrent.TimeUnit

interface IStoryProgressView {

    fun setFrontColor(@ColorInt color: Int)

    fun setBackColor(@ColorInt color: Int)

    fun setDuration(duration: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS)

    fun currentProgress(): Int

    fun doOnCancel(action: (view: IStoryProgressView) -> Unit)

    fun doOnStart(action: (view: IStoryProgressView) -> Unit)

    fun doOnEnd(action: (view: IStoryProgressView) -> Unit)

    fun start()

    fun cancel()

    fun pause()

    fun resume()

    fun end()

    fun fillInProgress()

    fun clearProgress()


}