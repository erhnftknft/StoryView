package com.erhn.ftknft.storyview.test

import com.erhn.ftknft.storyview.storyview.models.Snap

class PhotoSnap(val photoUrl: String) : Snap {

    override val type: Int
        get() = SnapType.PHOTO.type
}