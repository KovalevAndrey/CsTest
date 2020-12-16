package com.example.clearscoretest.presentation

sealed class CreditScoreViewEvent {

    object OnViewReloadClicked : CreditScoreViewEvent()
}