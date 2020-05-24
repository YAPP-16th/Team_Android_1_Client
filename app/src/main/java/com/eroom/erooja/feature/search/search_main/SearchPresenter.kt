package com.eroom.erooja.feature.search.search_main

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.goal.GetSearchGoalUseCase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.utils.addTo
import com.eroom.erooja.singleton.UserInfo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchPresenter(override val view:SearchContract.View,
                      private val  getMemberJobInterestsUseCase: GetMemberJobInterestsUseCase,
                      private val getSearchGoalUseCase: GetSearchGoalUseCase
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

    override fun getSearchJobInterest(interestId: Long?, page: Int) {
        getSearchGoalUseCase.getSearchJobInterest(interestId, size = 10, page = page, sortBy = SortBy.JOINCOUNT_DESC, uid = UserInfo.myUId)
            .subscribe ({
                if (it.totalPages > page) view.setAllView(it.content)
                view.setIsEnd(it.totalPages -1 <= page)
                view.stopAnimation()
            },{
                Timber.i(it.localizedMessage)
                view.stopAnimation()
            }) addTo compositeDisposable
    }

    private val mGoalContentCallback = object : DiffUtil.ItemCallback<GoalContent>() {
        override fun areItemsTheSame(oldItem: GoalContent, newItem: GoalContent): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: GoalContent, newItem: GoalContent): Boolean {
            return (oldItem.title == newItem.title) && (oldItem.id == newItem.id)
        }
    }

    fun getGoalContentCallback(): DiffUtil.ItemCallback<GoalContent> = mGoalContentCallback

    override fun onCleared() {
        compositeDisposable.clear()
    }
}