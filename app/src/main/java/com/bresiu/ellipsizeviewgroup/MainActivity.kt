package com.bresiu.ellipsizeviewgroup

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        val views = mutableListOf<View>()
        Log.d("BRS", "set 7 views immediately")
        repeat(7) {
            views.add(LayoutInflater.from(this).inflate(R.layout.attendee, ellipsize_view_group, false))
        }
        ellipsize_view_group.setViews(views)

        ////////////
        views.clear()
        repeat(5) {
            views.add(LayoutInflater.from(this).inflate(R.layout.attendee, ellipsize_view_group, false))
        }
        Handler().postDelayed({
            Log.d("BRS", "set 5 views delayed")
            ellipsize_view_group.setViews(views)
        }, 3000)
        Handler().postDelayed({
            Log.d("BRS", "set another view delayed")
            ellipsize_view_group.setViews(mutableListOf(
                LayoutInflater.from(this).inflate(R.layout.attendee, ellipsize_view_group, false),
                LayoutInflater.from(this).inflate(R.layout.attendee, ellipsize_view_group, false)))
        }, 5000)
        Handler().postDelayed({
            Log.d("BRS", "set another view delayed")
            ellipsize_view_group.setViews(mutableListOf(LayoutInflater.from(this).inflate(R.layout.attendee, ellipsize_view_group, false)))
        }, 7000)

    }
}
