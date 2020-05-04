package com.eroom.domain.api.service

import com.eroom.data.response.TodoDetailResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoService {
    @GET("todo/member/{uid}/goal/{goalId}")
    fun getUserTodoList( @Path("uid") uid: String,
                         @Path("goalId") goalId: Long) : Single<TodoDetailResponse>
}