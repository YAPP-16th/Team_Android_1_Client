package com.eroom.erooja.app.feature.othersPage

import android.annotation.SuppressLint
import com.eroom.data.localclass.Direction
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.member.PostOtherMemberInfoUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalsByUserIdUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class OthersPagePresenter(
    override val view: OthersPageContract.View,
    private val postOtherMemberInfoUseCase: PostOtherMemberInfoUseCase,
    private val getGoalsByUserIdUseCase: GetGoalsByUserIdUseCase
) : OthersPageContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getOngoingGoalList(uid: String, page: Int) {
        view.startAnimation()
        getGoalsByUserIdUseCase.getGoalsByUserId(
            uid,
            size = 10,
            page = page,
            sortBy = SortBy.END_DT.itemName,
            direction = Direction.ASC.itemName,
            end = false
        )
            .subscribe({
                view.setOngoingGoalListSizeOnTabLayout(it.totalElements)
                view.setOnGoingGoalPageIsEnd(it.totalPages - 1 <= page)
                view.setOngoingGoalList(it.content)
                view.stopAnimation()
            }, {
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getEndedGoalList(uid: String, page: Int) {
        view.startAnimation()
        getGoalsByUserIdUseCase.getGoalsByUserId(
            uid,
            size = 10,
            page = page,
            sortBy = SortBy.END_DT.itemName,
            direction = Direction.ASC.itemName,
            end = true
        )
            .subscribe({
                view.setEndedGoalListSizeOnTabLayout(it.totalElements)
                view.setEndedGoalPageIsEnd(it.totalPages - 1 <= page)
                view.setEndedGoalList(it.content)
                view.stopAnimation()
            }, {
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            })
    }

    @SuppressLint("CheckResult")
    override fun getOthersJobInterest(uid: String) {
        val othersJobInterest = ArrayList<String>()
        postOtherMemberInfoUseCase.postMemberInfo(uid)
            .subscribe({
                it.jobInterests.map {
                    othersJobInterest.add(it.name)
                }
                view.setOthersJobInterest(othersJobInterest)
            }, {
                Timber.e(it.localizedMessage)
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}