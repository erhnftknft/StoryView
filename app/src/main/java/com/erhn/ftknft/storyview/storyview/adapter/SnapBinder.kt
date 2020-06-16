package com.erhn.ftknft.storyview.storyview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import com.erhn.ftknft.storyview.storyview.models.Snap

abstract class SnapBinder<T : Snap>(@LayoutRes layoutId: Int, context: Context) {

    val itemView: View

    init {
        itemView = LayoutInflater.from(context).inflate(layoutId, null)
    }

    fun bind(item: Snap) {
        onBind(item as T)
    }

    abstract fun onBind(snap: T)

    abstract fun type(): Int

}