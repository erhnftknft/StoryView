package com.erhn.ftknft.storyview.storyview.adapter

import android.view.View
import com.erhn.ftknft.storyview.storyview.models.Snap

class SnapAdapter private constructor() {

    private val binders = ArrayList<SnapBinder<out Snap>>()

    private val snaps = ArrayList<Snap>()

    fun getBinder(snap: Snap): SnapBinder<out Snap> = binders.find { it.type() == snap.type }!!

    fun getViews() = binders.map { it.itemView }

    fun getViewByPosition(position: Int): View {
        val snap = snaps[position]
        return getBinder(snap).itemView
    }

    fun bind(position: Int) {
        val snap = snaps[position]
        getBinder(snap).bind(snap)
    }

    fun getItemSize(): Int = snaps.size

    fun setItems(items: List<out Snap>) {
        snaps.clear()
        snaps.addAll(items)
    }

    fun addBinder(snapBinder: SnapBinder<out Snap>) {
        binders.add(snapBinder)
    }

    class Builder {
        private val binders = ArrayList<SnapBinder<out Snap>>()

        fun addBinder(snapBinder: SnapBinder<out Snap>): Builder {
            val binder = binders.find { it.javaClass.simpleName == snapBinder.javaClass.simpleName }
            if (binder == null) {
                binders.add(snapBinder)
            }
            return this
        }

        fun build(): SnapAdapter {
            val adapter = SnapAdapter()
            adapter.binders.clear()
            adapter.binders.addAll(binders)
            return adapter
        }
    }

}