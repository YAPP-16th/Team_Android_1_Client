package com.eroom.erooja.feature.search.search_detail_frame

import android.annotation.SuppressLint
import com.eroom.data.entity.GoalContent
import com.eroom.domain.api.usecase.goal.GetSearchGoalUsecase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchResultPresenter(override var view: SearchResultContract.View,
                            private val getsearchgoalusecase: GetSearchGoalUsecase)
    : SearchResultContract.Presenter{

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getSearchJobInterest(interestId: Long?) {
        getsearchgoalusecase.getSearchJobInterest(interestId)
            .subscribe ({
                view.setAllView(it.content)
            },{
                Timber.i(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getSearchGoalTitle(title: String?) {
        getsearchgoalusecase.getSearchGoalTitle("TITLE", title)
            .subscribe({
                view.setAllView(it.content)

            },{
                Timber.i(it.localizedMessage)
            })
    }

    override fun checkRefresh(b: Boolean?,interestId: Long?) {
        if(b==true){
            getsearchgoalusecase.getSearchJobInterest(interestId)
                .subscribe({
                    view.updateView(it.content)
                },{
                    Timber.i(it.localizedMessage)
                })
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}