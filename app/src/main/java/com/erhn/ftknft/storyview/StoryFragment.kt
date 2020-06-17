package com.erhn.ftknft.storyview

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhn.ftknft.storyview.storyview.IStoryView
import com.erhn.ftknft.storyview.storyview.StoryView
import com.erhn.ftknft.storyview.storyview.adapter.SnapAdapter
import com.erhn.ftknft.storyview.storyview.progress.IStoryProgressView
import com.erhn.ftknft.storyview.test.PhotoSnap
import com.erhn.ftknft.storyview.test.PhotoSnapBinder
import kotlinx.android.synthetic.main.fragment_sotry.*

class StoryFragment : Fragment() {


    lateinit var snapAdapter: SnapAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val snaps =
            arrayListOf(
                PhotoSnap("https://images.unsplash.com/photo-1494548162494-384bba4ab999?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"),
                PhotoSnap("https://i.pinimg.com/originals/ca/76/0b/ca760b70976b52578da88e06973af542.jpg")
            )
        snapAdapter = SnapAdapter.Builder()
            .addBinder(PhotoSnapBinder(requireContext()))
            .build()
        snapAdapter.setItems(snaps)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sotry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sv.setSnapAdapter(snapAdapter)
        sv.start()
        sv.onEnd {
            Log.d("END", "${hashCode()}")
            (parentFragment as StoriesFragment).next()
        }

        sv.setOnLeftSideClick {
            sv.prevSnap()
        }

        sv.setOnRightSideClick {
            sv.nextSnap()
        }

        sv.setOnLongPress { action->
            when{
                action == IStoryView.LongPressAction.UP -> {
                    sv.resume()
                }
                action == IStoryView.LongPressAction.DOWN -> {
                    sv.pause()
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
        sv.cancel()
    }

    override fun onResume() {
        super.onResume()
        sv.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sv.cancel()
    }


}