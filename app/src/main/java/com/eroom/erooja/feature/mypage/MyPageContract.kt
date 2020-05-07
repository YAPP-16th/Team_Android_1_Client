package com.eroom.erooja.feature.mypage

import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent

interface MyPageContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(classList: ArrayList<JobClass>)
        fun saveUid(uid: String)
        fun setOngoingGoalList(list: ArrayList<MinimalGoalDetailContent>)
        fun setOnGoingGoalPageIsEnd(isEnd: Boolean)
        fun setEndedGoalPageIsEnd(isEnd: Boolean)
        fun setEndedGoalList(list: ArrayList<MinimalGoalDetailContent>)
    }

    interface Presenter {
        val view: View
        fun getUserInfo()
        fun getMemberJobInterest()
        fun onCleared()
        fun getOngoingGoalList(uid: String, page: Int)
        fun getEndedGoalList(uid: String, page: Int)
    }
}