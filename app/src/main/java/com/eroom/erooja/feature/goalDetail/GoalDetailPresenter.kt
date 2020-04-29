package com.eroom.erooja.feature.goalDetail

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.job.GetJobClassByIdUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class GoalDetailPresenter(override var view: GoalDetailContract.View,
                          private val getGoalDetailUseCase: GetGoalDetailUseCase,
                          private val getJobClassByIdUseCase: GetJobClassByIdUseCase
) :GoalDetailContract.Presenter{

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setView(it.title, it.description, it.joinCount, it.startDt, it.endDt)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getInterestedClassName(interestedIds: List<Long>) {
        Observable.fromIterable(interestedIds)
            .flatMap { getJobClassByIdUseCase.getJobClass(it) }
            .map {
                it.name
            }.toList()
            .subscribe({
                view.setInterestedClassName(it)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}