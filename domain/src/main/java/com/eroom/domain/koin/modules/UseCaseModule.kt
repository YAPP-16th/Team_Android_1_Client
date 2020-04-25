package com.eroom.domain.koin.modules

import com.eroom.domain.api.usecase.auth.PostKakaoLoginUseCase
import com.eroom.domain.api.usecase.auth.GetRefreshTokenUseCase
import com.eroom.domain.api.usecase.goal.GetInterestedGoalsUseCase
import com.eroom.domain.api.usecase.goal.PostNewGoalUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.api.usecase.member.*
import org.koin.dsl.module

val usecaseModule = module {
    factory { GetRefreshTokenUseCase(get(), get()) }

    factory { PostKakaoLoginUseCase(get(), get()) }

    factory { PostNicknameDuplicityUseCase(get()) }

    factory { GetJobGroupUseCase(get()) }

    factory { GetJobGroupAndClassUseCase(get()) }

    factory { PutNicknameUseCase(get()) }

    factory { PutJobInterestsUseCase(get()) }

    factory { GetMemberInfoUseCase(get()) }

    factory { GetMemberJobInterestsUseCase(get()) }

    factory { PostNewGoalUseCase(get()) }

    factory { GetInterestedGoalsUseCase(get()) }
}