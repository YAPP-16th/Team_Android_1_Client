package com.eroom.domain.api.service

import com.eroom.data.response.TodoDetailResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {
    @GET("todo")
    fun getUserTodoList( @Query("uid") uid: String,
                         @Query("goalId") goalId: Long) : Single<TodoDetailResponse>
}