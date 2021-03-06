package com.eroom.domain.koin.modules

import com.eroom.domain.api.usecase.alarm.GetAlarmsUseCase
import com.eroom.domain.api.usecase.alarm.GetUnCheckedAlarmsUseCase
import com.eroom.domain.api.usecase.alarm.PutAlarmIdUseCase
import com.eroom.domain.api.usecase.auth.PostKakaoLoginUseCase
import com.eroom.domain.api.usecase.auth.GetRefreshTokenUseCase
import com.eroom.domain.api.usecase.goal.*
import com.eroom.domain.api.usecase.job.GetJobClassByIdUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.api.usecase.member.*
import com.eroom.domain.api.usecase.membergoal.*
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import com.eroom.domain.api.usecase.todo.PutTodoEditUseCase
import com.eroom.domain.api.usecase.todo.PutTodoListUseCase
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

    factory { GetSearchGoalUseCase(get()) }

    factory { GetGoalDetailUseCase(get()) }

    factory { GetJobClassByIdUseCase(get()) }

    factory { GetGoalsByUserIdUseCase(get()) }

    factory { GetTodoListUseCase(get()) }

    factory { GetParticipantedListUseCase(get()) }

    factory { GetMemberProfileImagesUseCase(get())}

    factory { PutMemberProfileImagesUsecase(get())}

    factory { DeleteJobInterestsUseCase(get())}

    factory { PutTodoEditUseCase(get()) }
    
    factory { PostAddMyGoalUseCase(get()) }

    factory { GetGoalInfoByGoalIdUseCase(get()) }

    factory { PutTodoListUseCase(get()) }

    factory { PostMemberInfoUseCase(get()) }

    factory { PutGoalIsAbandonedUseCase(get())}

    factory { PostOtherMemberInfoUseCase(get()) }

    factory { PutGoalInfoUseCase(get()) }

    factory { PutGoalReparticipateUseCase(get())}

    factory { GetGoalInfoByUIdAndGoalIdUseCase(get())}

    factory { GetUnCheckedAlarmsUseCase(get()) }

    factory { PutAlarmIdUseCase(get()) }

    factory { GetAlarmsUseCase(get()) }
}
