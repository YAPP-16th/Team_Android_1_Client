package com.eroom.domain.api.service

import com.eroom.data.request.IdListRequest
import com.eroom.data.request.NicknameRequest
import com.eroom.data.request.UserProfileRequest
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.data.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.http.*
import android.R.attr.path



interface MemberService {
    @POST("member/nickname/duplicity")
    fun postNicknameDuplicity(@Body nickname: NicknameRequest): Single<Boolean>

    @PUT("member/nickname")
    fun putNickname(@Body nickname: NicknameRequest): Single<UserInfoResponse>

    @PUT("member/jobInterests")
    fun putJobInterests(@Body ids: IdListRequest): Single<Long>

    @GET("member")
    fun getMemberInfo(): Single<UserInfoResponse>

    @GET("member/jobInterests")
    fun getMemberJobInterests(): Single<ArrayList<JobGroupAndClassResponse>>

    @GET("member/images")
    fun getMemberProfileImages() : Single<Array<String?>>

    //@DELETE("member/jobInterests")
    @HTTP(method = "DELETE", path = "member/jobInterests", hasBody = true)
    fun deleteJobInterests(@Body ids: IdListRequest): Single<Long>

    @Headers("content-type: multipart/form-data")
    @Multipart
    @POST("member/image")
    fun putMemberProfileImage(@Part("multipartImageFile") multipartImageFile: UserProfileRequest) : Single<UserInfoResponse>
}