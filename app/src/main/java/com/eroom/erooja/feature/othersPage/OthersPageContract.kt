package com.eroom.erooja.feature.othersPage

import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent
import com.eroom.erooja.feature.mypage.MyPageContract

interface OthersPageContract {
    interface View {
        fun setNickname(nickname: String)
        //fun setJobInterestInfo(classList: ArrayList<JobClass>)
        fun setJobInterestInfo(jobInterestList: ArrayList<String>)
        fun saveUid(uid: String)
        fun setOngoingGoalList(list: ArrayList<MinimalGoalDetailContent>)
        fun setOnGoingGoalPageIsEnd(isEnd: Boolean)
        fun setEndedGoalPageIsEnd(isEnd: Boolean)
        fun setEndedGoalList(list: ArrayList<MinimalGoalDetailContent>)
        fun setOngoingGoalListSizeOnTabLayout(totalElements: Int)
        fun setEndedGoalListSizeOnTabLayout(totalElements: Int)
    }

    interface Presenter {
        val view: View
        //fun getMemberJobInterest()
        fun onCleared()
        fun getOngoingGoalList(uid: String, page: Int)
        fun getEndedGoalList(uid: String, page: Int)
    }
}