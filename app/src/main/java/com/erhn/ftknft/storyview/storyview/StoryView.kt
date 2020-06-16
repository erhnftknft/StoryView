package com.erhn.ftknft.storyview.storyview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.*
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.erhn.ftknft.storyview.R
import com.erhn.ftknft.storyview.storyview.adapter.SimpleAdapter
import com.erhn.ftknft.storyview.storyview.adapter.SnapAdapter
import com.erhn.ftknft.storyview.storyview.progress.StoryProgressView

class StoryView : ViewGroup, IStoryView {
    private var currentPlayed = 0

    @Px
    private var paddingBetweenProgresses: Int = 20

    @Px
    private var progressHeight: Int = 20

    @Px
    private var paddingStartProgress: Int = 0

    @Px
    private var paddingEndProgress: Int = 0

    @Px
    private var paddingTopProgress: Int = 0

    //colors
    @ColorInt
    private var backColor: Int = Color.DKGRAY

    @ColorInt
    private var frontColor: Int = Color.WHITE

    private val progressViews = ArrayList<StoryProgressView>()
    private val snapViews = ArrayList<View>()
    private var onEndAction: (() -> Unit)? = null

    var adapter: SnapAdapter = SimpleAdapter()

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typeArray = context.resources.obtainAttributes(attrs, R.styleable.StoryView)
        backColor = typeArray.getColor(R.styleable.StoryView_backProgressColor, Color.DKGRAY)
        frontColor = typeArray.getColor(R.styleable.StoryView_frontProgressColor, Color.WHITE)
        paddingBetweenProgresses =
            Math.round(typeArray.getDimension(R.styleable.StoryView_paddingBetweenProgress, 0f))
        paddingStartProgress =
            Math.round(typeArray.getDimension(R.styleable.StoryView_paddingStartProgress, 0f))
        paddingEndProgress =
            Math.round(typeArray.getDimension(R.styleable.StoryView_paddingEndProgress, 0f))
        paddingTopProgress =
            Math.round(typeArray.getDimension(R.styleable.StoryView_paddingTopProgress, 0f))
        progressHeight =
            Math.round(typeArray.getDimension(R.styleable.StoryView_heightPadding, 20f))
        typeArray.recycle()
        initialize(context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val actualWidth = MeasureSpec.getSize(widthMeasureSpec)
        val actualHeight = MeasureSpec.getSize(heightMeasureSpec)
        val itemSize = adapter.getItemSize()
        val availableSpace =
            actualWidth - (paddingStart + paddingEnd) - (paddingStartProgress + paddingEndProgress) - (paddingBetweenProgresses * (itemSize - 1))
        val oneProgressSpace = availableSpace / if (itemSize == 0) 1 else itemSize
        for (snapView in snapViews) {
            snapView.measure(actualWidth, actualHeight)
        }
        for (progressView in progressViews) {
            progressView.measure(oneProgressSpace, progressHeight)
        }
        setMeasuredDimension(actualWidth, actualHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val actualWidth = r - l
        val itemSize = adapter.getItemSize()
        val availableSpace =
            actualWidth - (paddingStart + paddingEnd) - (paddingStartProgress + paddingEndProgress) - (paddingBetweenProgresses * (itemSize - 1))
        val oneProgressSpace = availableSpace / if (itemSize == 0) 1 else itemSize
        for (snapView in snapViews) {
            snapView.layout(l, t, r, b)
        }
        for (i in 0 until progressViews.size) {
            val left =
                paddingStartProgress + paddingStart + i * (oneProgressSpace + paddingBetweenProgresses)
            val right = left + oneProgressSpace
            val top = paddingTopProgress + paddingTop
            val bottom = top + progressHeight
            progressViews[i].layout(left, top, right, bottom)
        }
    }



    override fun start() {
        for (i in 0 until progressViews.size) {
            progressViews[i].cancel()
            if (i == 0) {
                progressViews[i].start()
            }
        }
    }

    override fun pause() {
        if (progressViews.isEmpty()) return
        progressViews[currentPlayed].pause()
    }

    override fun resume() {
        if (progressViews.isEmpty()) return
        progressViews[currentPlayed].resume()
    }

    override fun onEnd(action: () -> Unit) {
        onEndAction = action
    }

    override fun setSnapAdapter(adapter: SnapAdapter) {
        this.adapter = adapter
        initialize(context)
    }

    private fun initialize(context: Context) {
        removeAllViews()
        progressViews.clear()
        snapViews.clear()
        val itemSize = adapter.getItemSize()
        snapViews.addAll(adapter.getViews())

        for (snapView in snapViews) {
            snapView.visibility = View.INVISIBLE
            addView(snapView)
        }
        for (i in 0 until itemSize) {
            val progressView =
                StoryProgressView(
                    context
                )
            progressView.setBackColor(backColor)
            progressView.setFrontColor(frontColor)
            progressView.doOnEnd {
                if (i < progressViews.lastIndex) {
                    adapter.getViewByPosition(i).visibility = View.INVISIBLE
                    progressViews[i + 1].start()
                } else if (i == progressViews.lastIndex) {
                    onEndAction?.invoke()
                }
            }

            progressView.doOnStart {
                currentPlayed = i
                adapter.bind(i)
                adapter.getViewByPosition(i).visibility = View.VISIBLE
            }
            progressViews.add(progressView)
            addView(progressView)
        }
    }

}