package com.eroom.erooja.feature.addGoal.newGoalPage.selectjob

import android.annotation.SuppressLint
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import io.reactivex.Observable
import timber.log.Timber

class SelectJobPresenter(override val view: SelectJobContract.View,
                         private val getJobGroupUseCase: GetJobGroupUseCase,
                         private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : SelectJobContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getJobGroups() {
        getJobGroupUseCase.getJobGroup()
            .subscribe({
                view.reRequestClassByGroup(it)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun getJobGroupAndClasses(groupIds: List<Long>) {
        Observable.fromIterable(groupIds)
            .flatMap { getJobGroupAndClassUseCase.getJobGroupAndClass(it) }
            .map {
                it
            }.toList()
            .subscribe({
                view.updateJobGroupAndClass(it)
            },{
                Timber.e(it.localizedMessage)
            })
    }
}