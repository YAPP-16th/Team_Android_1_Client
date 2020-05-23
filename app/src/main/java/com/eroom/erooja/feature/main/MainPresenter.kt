package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.Direction
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.goal.GetSearchGoalUseCase
import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalsByUserIdUseCase
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
    private val getSearchGoalUseCase: GetSearchGoalUseCase,
    private val getGoalsByUserIdUseCase: GetGoalsByUserIdUseCase
) : MainContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getUserInfo() {
        getMemberInfoUseCase.getUserInfo()
            .subscribe({
                view.setNickname(it.nickname)
                view.saveUid(it.uid)
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
    override fun getInterestedGoals(interestId: Long, uid: String) {
        getSearchGoalUseCase.getSearchJobInterest(interestId, 3, 0, SortBy.JOINCOUNT_ASC, uid)
            .subscribe({
                view.setNewGoalBrowse(it.content)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getMyParticipatedList(uid: String) {
        getGoalsByUserIdUseCase.getGoalsByUserId(uid, size = 5, page = 0, sortBy = SortBy.END_DT.itemName, direction = Direction.ASC.itemName, end = false)
            .subscribe({
                view.setParticipatedList(it.content)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    fun isGuest() = sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}