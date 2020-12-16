package com.example.clearscoretest.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.clearscoretest.R
import com.example.clearscoretest.presentation.CreditScoreViewEvent.OnViewReloadClicked
import com.example.clearscoretest.presentation.viewcomponent.DonutViewComponentImpl
import com.example.clearscoretest.utils.setVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CreditScoreActivity : AppCompatActivity() {

    private val viewModel: CreditScoreViewModel by viewModel { parametersOf(resources) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val errorView = findViewById<View>(R.id.reloadButton)
        val progressView = findViewById<View>(R.id.progressBar)
        val donutViewComponent = DonutViewComponentImpl(findViewById(R.id.donutView))
        errorView.setOnClickListener { viewModel.onViewEvent(OnViewReloadClicked) }

        viewModel.userScoreViewState.observe(this, { result ->
            when (result) {
                is Result.Success -> {
                    progressView.setVisible(false)
                    errorView.setVisible(false)
                    with(result.data) {
                        donutViewComponent.setVisibility(isVisible = true)
                        donutViewComponent.setProgress(progress = percentage)
                        donutViewComponent.setSubtitle(subtitle = subtitle)
                        donutViewComponent.setValue(value = value)
                        donutViewComponent.setTitle(title = title)
                    }

                }
                is Result.Error -> {
                    progressView.setVisible(false)
                    errorView.setVisible(true)
                    donutViewComponent.setVisibility(isVisible = false)
                }
                is Result.Loading -> {
                    progressView.setVisible(true)
                    errorView.setVisible(false)
                    donutViewComponent.setVisibility(isVisible = false)
                }
            }
        })

    }
}