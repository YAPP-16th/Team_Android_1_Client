package com.eroom.erooja.feature.mypage

import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent

interface MyPageContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(classList: ArrayList<JobClass>)
        fun setParticipatedList(list: ArrayList<MinimalGoalDetailContent>)
        fun saveUid(uid: String)
    }

    interface Presenter {
        val view: View
        fun getUserInfo()
        fun getMemberJobInterest()
        fun onCleared()
        fun getMyParticipatedList(uid: String)
    }
}