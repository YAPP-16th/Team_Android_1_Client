package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.domain.api.usecase.goal.GetInterestedGoalsUseCase
import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class MainPresenter(
    override val view: MainContract.View,
    private val sharedPrefRepository: SharedPrefRepository,
    private val getMemberInfoUseCase: GetMemberInfoUseCase,
    private val getMemberJobInterestUseCase: GetMemberJobInterestsUseCase,
    private val getInterestedGoalsUseCase: GetInterestedGoalsUseCase
) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getUserInfo() {
        getMemberInfoUseCase.getUserInfo()
            .subscribe({
                view.setNickname(it.nickname)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getMemberJobInterest() {
        getMemberJobInterestUseCase.getMemberJobInterests()
            .subscribe({
                val classList = ArrayList<JobClass>()
                for (group in it) {
                    group.jobInterests.map { jobClass ->
                        classList.add(jobClass)
                    }
                }
                if (classList.size != 0) {
                    val a = Math.random() * 100
                    val index = (a % classList.size).toInt()
                    view.setJobInterestInfo(classList[index].name, classList[index].id, classList)
                }
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getInterestedGoals(interestId: Long) {
        getInterestedGoalsUseCase.getInterestedGoals(interestId, 3, 0)
            .subscribe({
                view.setNewGoalBrowse(it.content)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    fun isGuest() = sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}