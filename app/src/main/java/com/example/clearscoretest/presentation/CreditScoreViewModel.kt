package com.example.clearscoretest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clearscoretest.domain.GetUserCreditScoreUseCase
import com.example.clearscoretest.presentation.CreditScoreViewEvent.OnViewReloadClicked
import com.example.clearscoretest.presentation.Result.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CreditScoreViewModel(
    private val getUserCreditScoreUseCase: GetUserCreditScoreUseCase,
    private val creditScoreModelMapper: CreditScoreModelMapper
) :
    ViewModel() {

    private var disposable: Disposable? = null

    private val _userScoreViewState = MutableLiveData<Result<CreditScoreModel>>()
    val userScoreViewState: LiveData<Result<CreditScoreModel>>
        get() = _userScoreViewState

    init {
        loadCreditScore()
    }

    fun onViewEvent(event: CreditScoreViewEvent) {
        when (event) {
            is OnViewReloadClicked -> loadCreditScore()
        }
    }

    private fun loadCreditScore() {
        disposable?.dispose()
        disposable = getUserCreditScoreUseCase()
            .toObservable()
            .map<Result<CreditScoreModel>> { Success(creditScoreModelMapper.convert(it)) }
            .onErrorReturn { Error() }
            .startWith(Loading())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _userScoreViewState.value = it }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}