package com.eroom.erooja.feature.search.search_main

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.DesignClass
import com.eroom.data.localclass.DevelopClass
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchPresenter(override val view:SearchContract.View,
                      private val  getMemberJobInterestsUseCase: GetMemberJobInterestsUseCase
): SearchContract.Presenter{

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getAlignedJobInterest() {
        getMemberJobInterestsUseCase.getMemberJobInterests()
            .subscribe({
                val userJobInterest = mutableSetOf<String>()
                val userJobInterestList = mutableSetOf<JobClass>()
                for (group in it) {
                    group.jobInterests.map { jobclass ->
                        userJobInterest.add(jobclass.name)
                        userJobInterestList.add(jobclass)
                    }
                }
                view.setAlignedJobInterest(userJobInterest)
                view.setUserJobInterest(userJobInterestList)
            },{
                Timber.i(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}