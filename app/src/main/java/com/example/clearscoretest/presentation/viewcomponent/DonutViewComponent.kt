package com.example.clearscoretest.presentation.viewcomponent

import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.IntRange
import com.example.clearscoretest.R
import com.example.clearscoretest.utils.setVisible

interface DonutViewComponent {

    fun setTitle(title: String)

    fun setSubtitle(subtitle: String)

    fun setValue(value: String)

    fun setProgress(@IntRange(from = 0, to = 100) progress: Int)

    fun setVisibility(isVisible: Boolean)

}

class DonutViewComponentImpl(private val donutViewRoot: ViewGroup) : DonutViewComponent {

    private val titleView = donutViewRoot.findViewById<TextView>(R.id.titleTextView)
    private val valueView = donutViewRoot.findViewById<TextView>(R.id.valueTextView)
    private val subtitleView = donutViewRoot.findViewById<TextView>(R.id.subtitleTextView)
    private val donutProgress =
        donutViewRoot.findViewById<ProgressBar>(R.id.donutProgressView)

    override fun setTitle(title: String) {
        titleView.text = title
    }

    override fun setSubtitle(subtitle: String) {
        subtitleView.text = subtitle
    }

    override fun setValue(value: String) {
        valueView.text = value
    }

    override fun setProgress(progress: Int) {
        donutProgress.progress = progress
    }

    override fun setVisibility(isVisible: Boolean) {
        donutViewRoot.setVisible(isVisible)
    }

}