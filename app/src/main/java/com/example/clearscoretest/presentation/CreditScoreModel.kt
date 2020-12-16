package com.example.clearscoretest.presentation

import androidx.annotation.IntRange

class CreditScoreModel(
    val title: String,
    val subtitle: String,
    val value: String,
    @IntRange(from = 0L, to = 100L) val percentage: Int
)
