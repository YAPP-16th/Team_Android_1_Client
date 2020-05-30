package com.eroom.erooja.app.feature.addGoal.newGoalPage.selectjob

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SelectJobPresenter(
    override val view: SelectJobContract.View,
    private val getJobGroupUseCase: GetJobGroupUseCase,
    private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : SelectJobContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getJobGroups() {
        getJobGroupUseCase.getJobGroup()
            .subscribe({
                view.reRequestClassByGroup(it)
            }, {
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getJobGroupAndClasses(groupIds: List<Long>) {
        Observable.fromIterable(groupIds)
            .concatMap { getJobGroupAndClassUseCase.getJobGroupAndClass(it) }
            .map {
                it
            }.toList()
            .subscribe({
                view.updateJobGroupAndClass(it)
                view.stopAnimation()
            }, {
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}