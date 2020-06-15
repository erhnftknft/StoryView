package com.erhn.ftknft.storyview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spv.doOnStart {
            Log.d("AMAZING","onStart")
        }
        spv.doOnCancel {
            Log.d("AMAZING","onCancel")
        }
        spv.doOnEnd {
            Log.d("AMAZING","onEnd")
        }
        spv.start()
    }
}