package com.eroom.erooja.feature.setting

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class SettingPresenter(override var view: SettingContract.View,
                       private val  getMemberJobInterestsUseCase: GetMemberJobInterestsUseCase)
    :SettingContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getSettingList(list: Array<String>) {
        view.setList(list)
    }

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
                view.setUserJobInterest(userJobInterestList)
            },{
                Timber.i(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}