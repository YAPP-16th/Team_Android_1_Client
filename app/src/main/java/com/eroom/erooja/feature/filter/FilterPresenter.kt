package com.eroom.erooja.feature.filter

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import io.reactivex.Observable
import timber.log.Timber

class FilterPresenter(override val view: FilterContract.View,
                      private val getJobGroupUseCase: GetJobGroupUseCase,
                      private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : FilterContract.Presenter {

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