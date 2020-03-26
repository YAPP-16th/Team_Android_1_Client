package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.GetDummyUseCase

class MainPresenter(override val view: MainContract.View,
                    private val getDummyUseCase: GetDummyUseCase): MainContract.Presenter {

    @SuppressLint("CheckResult")
    fun test() = getDummyUseCase.getDummyData()
            .subscribe({

            },{

            })
}