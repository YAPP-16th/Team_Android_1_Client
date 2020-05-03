package com.eroom.erooja.feature.otherList

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import timber.log.Timber

class OtherListPresenter(override var view: OtherListContract.View,
                         var getTodoListUseCase: GetTodoListUseCase)
    : OtherListContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getData(uid: String, goalId: Long){
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                view.setAllView(it.content)
            },{
                Timber.e(it.localizedMessage)
            })
    }
}