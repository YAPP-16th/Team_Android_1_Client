package com.eroom.domain.api.usecase.member

import com.eroom.data.request.UIdRequest
import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import com.eroom.domain.koin.repository.GuestRetrofitRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostOtherMemberInfoUseCase(accessTokenRetrofitRepository: AccessRetrofitRepository) {
    private val memberService = accessTokenRetrofitRepository.getAccessRetrofit()
        .create(MemberService::class.java)

    fun postMemberInfo(uid: String) = memberService
        .postMemberInfo(UIdRequest(uid))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
