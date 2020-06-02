package com.eroom.erooja.feature.addDirectList.inactivejob

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class InactiveJobPresenter(
    override val view: InactiveJobContract.View,
    private val getJobGroupUseCase: GetJobGroupUseCase,
    private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase,
    private val getGoalDetailUseCase: GetGoalDetailUseCase
) : InactiveJobContract.Presenter {

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

    override fun getJobInterestOfTodoList(goalId: Long?) {
        goalId?.let {
            getGoalDetailUseCase.getGoalDetail(goalId)
                .subscribe({
                    view.setJobInterestOfTodoList(it.jobInterests.map {
                        it.id
                    })
                }, {
                    view.stopAnimation()
                })
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}