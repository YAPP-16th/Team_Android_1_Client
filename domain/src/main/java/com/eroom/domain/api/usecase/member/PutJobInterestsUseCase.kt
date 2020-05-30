package com.eroom.domain.api.usecase.member

import com.eroom.data.request.IdListRequest
import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PutJobInterestsUseCase(retrofitRepository: AccessRetrofitRepository) {
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(MemberService::class.java)

    fun putJobInterests(ids: ArrayList<Long>): Single<Long> = memberService
        .putJobInterests(IdListRequest(ids))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}