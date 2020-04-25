package com.eroom.erooja.feature.filter

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FilterPresenter(override val view: FilterContract.View,
                      private val getJobGroupUseCase: GetJobGroupUseCase,
                      private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : FilterContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getJobGroups() {
        getJobGroupUseCase.getJobGroup()
            .subscribe({
                view.reRequestClassByGroup(it)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
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
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}