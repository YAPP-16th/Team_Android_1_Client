package com.eroom.domain.api.usecase.member

import com.eroom.data.request.NicknameRequest
import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.RetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetNicknameDuplicityUseCase(retrofitRepository: RetrofitRepository) {
    private val memberService = retrofitRepository
        .getGuestRetrofit()
        .create(MemberService::class.java)

    fun postKakaoLogin(nickname: String): Single<Boolean> = memberService
        .getNicknameDuplicity(NicknameRequest(nickname))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}