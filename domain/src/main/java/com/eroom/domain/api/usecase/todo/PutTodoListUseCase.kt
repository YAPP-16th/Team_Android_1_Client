package com.eroom.domain.api.usecase.todo

import com.eroom.data.entity.Todo
import com.eroom.data.request.TodoRequest
import com.eroom.domain.api.service.TodoService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PutTodoListUseCase(accessTokenRepository: AccessRetrofitRepository) {
    private val todoService = accessTokenRepository
        .getAccessRetrofit()
        .create(TodoService::class.java)

    fun putTodoList(goalId: Long, todoList: ArrayList<Todo>) = todoService
        .putTodoList(TodoRequest(goalId, todoList))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}