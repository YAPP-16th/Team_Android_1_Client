package com.eroom.erooja.feature.search.search_main

import android.annotation.SuppressLint
import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.DesignClass
import com.eroom.data.localclass.DevelopClass
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.api.usecase.goal.GetSearchGoalUsecase
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.addTo
import com.eroom.erooja.feature.search.search_detail_frame.SearchResultAdapter
import com.eroom.erooja.feature.search.search_detail_frame.SearchResultFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_result_list.*
import timber.log.Timber

class SearchPresenter(override val view:SearchContract.View,
                      private val  getMemberJobInterestsUseCase: GetMemberJobInterestsUseCase,
                      private val getsearchgoalusecase: GetSearchGoalUsecase
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


    @SuppressLint("CheckResult")
    override fun getSearchJobInterest(interestId: Long?) {
        getsearchgoalusecase.getSearchJobInterest(interestId)
            .subscribe ({
                view.checkContentSize(it.content.size)
                //view.setAllView(it.content)
            },{
                Timber.i(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}