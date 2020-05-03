package com.eroom.erooja.feature.goalDetail

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.domain.api.usecase.goal.GetGoalDetailUseCase
import com.eroom.domain.api.usecase.job.GetJobClassByIdUseCase
import com.eroom.domain.api.usecase.membergoal.GetGoalsByUserIdUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class GoalDetailPresenter(override var view: GoalDetailContract.View,
                          private val getGoalDetailUseCase: GetGoalDetailUseCase,
                          private val getJobClassByIdUseCase: GetJobClassByIdUseCase,
                          private val getGoalsByUserIdUseCase: GetGoalsByUserIdUseCase
) :GoalDetailContract.Presenter{

    val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    override fun getData(goalId: Long) {
        getGoalDetailUseCase.getGoalDetail(goalId)
            .subscribe({
                view.setView(it.title, it.description, it.joinCount, it.startDt, it.endDt)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getInterestedClassName(interestedIds: List<Long>) {
        Observable.fromIterable(interestedIds)
            .flatMap { getJobClassByIdUseCase.getJobClass(it) }
            .map {
                it.name
            }.toList()
            .subscribe({
                view.setInterestedClassName(it)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getMinimalTodoList(goalId: Long) {
        getGoalsByUserIdUseCase.getTodoByGoalId(goalId)
            .subscribe({
                view.setRecyclerView(it.content)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    private val mGoalContentCallback = object : DiffUtil.ItemCallback<MinimalTodoListContent>() {
        override fun areItemsTheSame(oldItem: MinimalTodoListContent, newItem: MinimalTodoListContent): Boolean {
            return oldItem.uid.equals(newItem.uid)
        }
        override fun areContentsTheSame(oldItem: MinimalTodoListContent, newItem: MinimalTodoListContent): Boolean {
            return (oldItem.todoList[0].content.equals(newItem.todoList[0].content)) && (oldItem.uid.equals(newItem.uid))
        }
    }

    fun getGoalContentCallback(): DiffUtil.ItemCallback<MinimalTodoListContent> = mGoalContentCallback


    override fun onCleared() {
        compositeDisposable.clear()
    }
}