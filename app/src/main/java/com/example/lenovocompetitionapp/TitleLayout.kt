package com.example.lenovocompetitionapp

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var mTitle: TextView
    var mReturn: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        mTitle = findViewById(R.id.text_title)
        mReturn = findViewById(R.id.text_return)

        mReturn.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }
    }
}