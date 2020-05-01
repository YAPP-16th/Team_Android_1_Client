package com.eroom.erooja.feature.ongoingGoal

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import timber.log.Timber

class OngoingGoalPresenter(override var view: OngoingGoalContract.View,
                           private val getGoalDetailUseCase: GetGoalDetailUseCase
): OngoingGoalContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setGoalData(it)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    override fun getTodoData(goalId: Long) {

    }
}