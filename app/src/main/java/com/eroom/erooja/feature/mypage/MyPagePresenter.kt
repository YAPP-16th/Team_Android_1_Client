package com.eroom.erooja.feature.mypage

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.data.localclass.Direction
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalsByUserIdUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.addTo
import com.eroom.erooja.feature.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class MyPagePresenter(
    override val view: MyPageContract.View,
    private val sharedPrefRepository: SharedPrefRepository,
    private val getMemberInfoUseCase: GetMemberInfoUseCase,
    private val getMemberJobInterestUseCase: GetMemberJobInterestsUseCase,
    private val getGoalsByUserIdUseCase: GetGoalsByUserIdUseCase
) : MyPageContract.Presenter {
    private val compositeDisposable = CompositeDisposable()

    override fun getUserInfo() {
        getMemberInfoUseCase.getUserInfo()
            .subscribe({
                view.setNickname(it.nickname)
                view.saveUid(it.uid)
            }, {
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
                view.setJobInterestInfo(classList)
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getMyParticipatedList(uid: String, page: Int) {
        getGoalsByUserIdUseCase.getGoalsByUserId(uid, size = 5, page = page, sortBy = SortBy.END_DT.itemName, direction = Direction.ASC.itemName, endDtIsBeforeNow = false)
            .subscribe({
                view.setParticipatedList(it.content)
                var a = it.totalPages
                var b = page
                view.setIsEnd(it.totalPages <= page)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    fun isGuest() = sharedPrefRepository.getPrefsBooleanValue(Consts.IS_GUEST)

    private val mMinimalGoalDetailContentCallback= object : DiffUtil.ItemCallback<MinimalGoalDetailContent>() {
        override fun areItemsTheSame(oldItem: MinimalGoalDetailContent, newItem: MinimalGoalDetailContent): Boolean {
            return oldItem.goalId == newItem.goalId
        }

        override fun areContentsTheSame(oldItem: MinimalGoalDetailContent, newItem: MinimalGoalDetailContent): Boolean {
            return oldItem == newItem
        }
    }

    fun getMinimalGoalDetailContentCallback(): DiffUtil.ItemCallback<MinimalGoalDetailContent> = mMinimalGoalDetailContentCallback

    override fun onCleared() {
        compositeDisposable.clear()
    }

}