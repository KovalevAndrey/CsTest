package com.example.clearscoretest.di

import android.content.res.Resources
import com.example.clearscoretest.presentation.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val presentationModule = module {

    viewModel { (resources: Resources) ->
        CreditScoreViewModel(
            getUserCreditScoreUseCase = get(),
            creditScoreModelMapper = get { parametersOf(resources) })
    }

    factory<CreditScoreModelMapper> { (resources: Resources) ->
        CreditScoreModelMapperImpl(get { parametersOf(resources) })
    }

    factory<CreditScoreResourceProvider> { (resources: Resources) ->
        CreditScoreResourceProviderImpl(resources)
    }
}
