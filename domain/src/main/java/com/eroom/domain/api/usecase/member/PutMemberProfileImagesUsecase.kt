package com.eroom.domain.api.usecase.member

import com.eroom.data.request.UserProfileRequest
import com.eroom.data.response.UserInfoResponse
import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class PutMemberProfileImagesUsecase(retrofitRepository: AccessRetrofitRepository) {
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(MemberService::class.java)

    fun putMemberProfileImages(file: File) : Single<UserInfoResponse> = memberService
        .putMemberProfileImage(UserProfileRequest(file))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}