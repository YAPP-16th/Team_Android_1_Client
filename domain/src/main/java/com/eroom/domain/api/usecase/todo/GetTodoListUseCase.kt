package com.eroom.domain.api.usecase.todo

import com.eroom.data.response.TodoDetailResponse
import com.eroom.domain.api.service.TodoService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetTodoListUseCase(retrofitRepository: AccessRetrofitRepository){
    private val TodoService = retrofitRepository
        .getAccessRetrofit()
        .create(TodoService::class.java)

    fun getUserTodoList(uid: String, goalId: Long) : Single<TodoDetailResponse> = TodoService
        .getUserTodoList(uid, goalId, size = Integer.MAX_VALUE, page = 0)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}