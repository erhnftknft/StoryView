package com.erhn.ftknft.storyview.test

import android.content.Context
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.erhn.ftknft.storyview.R
import com.erhn.ftknft.storyview.storyview.adapter.SnapBinder
import kotlinx.android.synthetic.main.snap_layout.view.*

class PhotoSnapBinder(context: Context) :
    SnapBinder<PhotoSnap>(R.layout.snap_layout, context) {

    override fun onBind(snap: PhotoSnap) {
        Glide.with(itemView).load(snap.photoUrl).override(itemView.width, itemView.height)
            .into(itemView.imgSnap)
    }

    override fun type(): Int = SnapType.PHOTO.type
}