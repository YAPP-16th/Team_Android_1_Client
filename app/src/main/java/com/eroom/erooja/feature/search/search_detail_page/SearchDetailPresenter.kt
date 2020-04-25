package com.eroom.erooja.feature.search.search_detail_page

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.goal.GetSearchGoalUsecase
import timber.log.Timber

class SearchDetailPresenter(override val view: SearchDetailContract.View,
                            private val getsearchgoalusecase:GetSearchGoalUsecase)
    : SearchDetailContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getUserGoal(jobInterestIds: Long) {
        getsearchgoalusecase.getSearchGoal(jobInterestIds)
            .subscribe ({
                view.searchGoal(it)
            },{
                Timber.i(it.localizedMessage)
            })
    }
}