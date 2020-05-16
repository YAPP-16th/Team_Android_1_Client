package com.eroom.erooja.feature.goalEdit

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.PutGoalInfoUseCase
import timber.log.Timber

class GoalEditPresenter(override val view: GoalEditContract.View,
                        private val putGoalInfoUseCase: PutGoalInfoUseCase
): GoalEditContract.Presenter {
    @SuppressLint("CheckResult")
    override fun requestEditGoal(goalId: Long, title: String, description: String) {
        putGoalInfoUseCase.putGoalInfo(goalId, title, description)
            .subscribe({
                view.finishEdit()
            },{
                Timber.e(it.localizedMessage)
                view.showMessage()
            })
    }
}