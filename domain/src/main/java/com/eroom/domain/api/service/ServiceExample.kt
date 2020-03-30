package com.eroom.domain.api.service

import com.eroom.data.response.ResponseExample
import io.reactivex.Single
import retrofit2.http.GET

interface ServiceExample {
    @GET("test/dummy")
    fun getDummy(): Single<ResponseExample>
}