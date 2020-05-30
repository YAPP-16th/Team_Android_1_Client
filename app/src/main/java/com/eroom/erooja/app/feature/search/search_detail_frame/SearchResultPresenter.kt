package com.eroom.erooja.app.feature.search.search_detail_frame

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.GoalContent
import com.eroom.data.localclass.SortBy
import com.eroom.domain.api.usecase.goal.GetSearchGoalUseCase
import com.eroom.domain.utils.addTo
import com.eroom.erooja.app.singleton.UserInfo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchResultPresenter(
    override var view: SearchResultContract.View,
    private val getSearchGoalUseCase: GetSearchGoalUseCase
) : SearchResultContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getSearchJobInterest(interestId: Long?, page: Int) {
        getSearchGoalUseCase.getSearchJobInterest(
            interestId,
            size = 10,
            page = page,
            sortBy = SortBy.JOINCOUNT_DESC,
            uid = UserInfo.myUId
        )
            .subscribe({
                view.setAllView(it.content)
                view.setIsEnd(it.totalPages <= page)
            }, {
                Timber.i(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getSearchGoalTitle(title: String?, page: Int) {
        getSearchGoalUseCase.getSearchGoalTitle(SortBy.TITLE, title, size = 10, page = page)
            .subscribe({
                view.setAllView(it.content)
                view.setIsEnd(it.totalPages <= page)
            }, {
                Timber.i(it.localizedMessage)
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