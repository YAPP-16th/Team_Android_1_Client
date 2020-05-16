package com.eroom.erooja.feature.othersPage

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.Direction
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalsByUserIdUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.addTo
import com.eroom.erooja.feature.mypage.MyPageContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class OthersPagePresenter (
    override val view: OthersPageContract.View,
    //private val getMemberJobInterestUseCase: GetMemberJobInterestsUseCase,
    private val getGoalsByUserIdUseCase: GetGoalsByUserIdUseCase
) : OthersPageContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

//    @SuppressLint("CheckResult")
//    override fun getMemberJobInterest() {
//        getMemberJobInterestUseCase.getMemberJobInterests()
//            .subscribe({
//                val classList = ArrayList<JobClass>()
//                for (group in it) {
//                    group.jobInterests.map { jobClass ->
//                        classList.add(jobClass)
//                    }
//                }
//                view.setJobInterestInfo(classList)
//            }, {
//                Timber.e(it.localizedMessage)
//            }) addTo compositeDisposable
//    }

    @SuppressLint("CheckResult")
    override fun getOngoingGoalList(uid: String, page: Int) {
        getGoalsByUserIdUseCase.getGoalsByUserId(uid, size = 5, page = page, sortBy = SortBy.END_DT.itemName, direction = Direction.ASC.itemName, end = false)
            .subscribe({
                view.setOngoingGoalListSizeOnTabLayout(it.totalElements)
                view.setOnGoingGoalPageIsEnd(it.totalPages - 1 <= page)
                view.setOngoingGoalList(it.content)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getEndedGoalList(uid: String, page: Int) {
        getGoalsByUserIdUseCase.getGoalsByUserId(uid, size = 5, page = page, sortBy = SortBy.END_DT.itemName, direction = Direction.ASC.itemName, end = true)
            .subscribe({
                view.setEndedGoalListSizeOnTabLayout(it.totalElements)
                view.setEndedGoalPageIsEnd(it.totalPages - 1 <= page)
                view.setEndedGoalList(it.content)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}