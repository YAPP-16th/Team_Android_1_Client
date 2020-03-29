package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.GetDummyUseCase
import com.eroom.domain.koin.repository.SharedPrefRepository

class ExamPresenter(override val view: ExamContract.View,
                    private val getDummyUseCase: GetDummyUseCase,
                    private val sharedPrefRepository: SharedPrefRepository): ExamContract.Presenter {

    @SuppressLint("CheckResult")
    override fun testGet() = getDummyUseCase.getDummyData()
            .subscribe({

            },{

            })

    fun test2() = sharedPrefRepository.writePrefs("key", "value")

}