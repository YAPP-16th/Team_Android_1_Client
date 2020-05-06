package com.eroom.domain.api.usecase.member

import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetMemberProfileImages(retrofitRepository: AccessRetrofitRepository){
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(MemberService::class.java)

    fun getMemberProfileImages() : Single<Array<String?>> = memberService
        .getMemberProfileImages()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}