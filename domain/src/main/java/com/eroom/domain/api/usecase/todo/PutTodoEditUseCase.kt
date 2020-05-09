package com.eroom.domain.api.usecase.todo

import com.eroom.data.request.TodoEditRequest
import com.eroom.data.response.TodoDetailResponse
import com.eroom.data.response.TodoEditResponse
import com.eroom.domain.api.service.TodoService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PutTodoEditUseCase(retrofitRepository: AccessRetrofitRepository){
    private val TodoService = retrofitRepository
        .getAccessRetrofit()
        .create(TodoService::class.java)

    fun putTodoEdit(todoId: Long, boolean: Boolean) : Single<TodoEditResponse> = TodoService
        .putTodoId(todoId, TodoEditRequest(if (boolean) "true" else "false"))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}