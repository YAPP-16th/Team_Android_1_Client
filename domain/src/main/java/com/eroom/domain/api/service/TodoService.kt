package com.eroom.domain.api.service

import com.eroom.data.request.TodoEditRequest
import com.eroom.data.request.TodoRequest
import com.eroom.data.response.TodoDetailResponse
import com.eroom.data.response.TodoEditResponse
import com.eroom.data.response.TodoResponse
import io.reactivex.Single
import retrofit2.http.*

interface TodoService {
    @GET("todo")
    fun getUserTodoList(@Query("uid") uid: String,
                        @Query("goalId") goalId: Long,
                        @Query("page") page: Int,
                        @Query("size") size: Int
    ) : Single<TodoDetailResponse>

    @PUT("todo/{todoId}")
    fun putTodoId(@Path("todoId") todoId: Long, @Body todoEditRequest: TodoEditRequest): Single<TodoEditResponse>

    @PUT("todo")
    fun putTodoList(@Body todoRequest: TodoRequest): Single<ArrayList<TodoResponse>>
}