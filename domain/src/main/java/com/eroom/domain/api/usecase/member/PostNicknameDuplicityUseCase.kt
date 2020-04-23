package com.eroom.domain.api.usecase.member

import com.eroom.data.request.NicknameRequest
import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostNicknameDuplicityUseCase(retrofitRepository: AccessRetrofitRepository) {
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(MemberService::class.java)

    fun postCheckNickname(nickname: String): Single<Boolean> = memberService
        .postNicknameDuplicity(NicknameRequest(nickname))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}