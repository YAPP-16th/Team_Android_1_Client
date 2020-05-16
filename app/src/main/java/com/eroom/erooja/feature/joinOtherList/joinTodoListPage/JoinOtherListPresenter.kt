package com.eroom.erooja.feature.joinOtherList.joinTodoListPage

import com.eroom.data.entity.TodoList
import com.eroom.data.request.AddMyGoalRequest
import com.eroom.domain.api.usecase.membergoal.PostAddMyGoalUseCase
import com.eroom.domain.utils.addTo
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class JoinOtherListPresenter(
    override var view: JoinOtherListContract.View,
    private val postAddMyGoalUseCase: PostAddMyGoalUseCase
) : JoinOtherListContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun addMyGoal(
        goalId: Long?,
        ownerUid: String?,
        endDt: String,
        todoList: ArrayList<String>
    ) {
        val todoListResult = ArrayList<TodoList>()
        var index: Long = 0
        for (todo in todoList) {
            todoListResult.add(TodoList(todo, index))
            index++
        }

        postAddMyGoalUseCase.postAddMyGoal(
            AddMyGoalRequest(
                goalId, ownerUid,
                endDt, todoList = todoListResult
            )
        )
            .subscribe({
                it.body()?.goalId?.let { id -> view.redirectNewGoalFinish(id) }
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }
}