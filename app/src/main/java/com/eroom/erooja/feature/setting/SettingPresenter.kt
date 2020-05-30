package com.eroom.erooja.feature.setting

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.domain.api.usecase.member.*
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SettingPresenter(
    override var view: SettingContract.View,
    private val getMemberJobInterestsUseCase: GetMemberJobInterestsUseCase,
    private val deleteJobInterestsUescase: DeleteJobInterestsUseCase,
    private val putJobInterestsUseCase: PutJobInterestsUseCase,
    private val sharedPrefRepository: SharedPrefRepository
) : SettingContract.Presenter {

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
            }, {
                Timber.i(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun updateJobInterest(original: ArrayList<Long>, updated: ArrayList<Long>) {
        deleteJobInterestsUescase.deleteJobInterests(original)
            .subscribe({
                putJobInterestsUseCase.putJobInterests(updated)
                    .subscribe({
                    }, {
                        it.localizedMessage
                    })
            }, {
                it.localizedMessage
            })
        view.InformUpdatedMsg()

    }

    override fun logout() {
        sharedPrefRepository.apply {
            writePrefs(Consts.AUTO_LOGIN, false)
            writePrefs(Consts.REFRESH_TOKEN, "")
            writePrefs(Consts.IS_GUEST, false)
            writePrefs(Consts.ACCESS_TOKEN, "")
        }
        view.logoutCompleted()
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}