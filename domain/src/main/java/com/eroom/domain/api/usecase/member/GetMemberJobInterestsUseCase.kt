package com.eroom.domain.api.usecase.member

import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.api.service.MemberService
import com.eroom.domain.koin.repository.AccessRetrofitRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetMemberJobInterestsUseCase(retrofitRepository: AccessRetrofitRepository) {
    private val memberService = retrofitRepository
        .getAccessRetrofit()
        .create(MemberService::class.java)

    fun getMemberJobInterests(): Single<ArrayList<JobGroupAndClassResponse>> = memberService
        .getMemberJobInterests()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}