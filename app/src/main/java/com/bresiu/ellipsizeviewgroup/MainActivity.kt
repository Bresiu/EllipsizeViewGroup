package com.bresiu.ellipsizeviewgroup

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        repeat(9) {
            LayoutInflater.from(this).inflate(R.layout.attendee, ellipsize_view_group, true) }
    }
}
