package com.eroom.erooja.app.feature.othersPage

import com.eroom.data.entity.MinimalGoalDetailContent

interface OthersPageContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(jobInterestList: ArrayList<String>)
        fun saveUid(uid: String)
        fun setOngoingGoalList(list: ArrayList<MinimalGoalDetailContent>)
        fun setOnGoingGoalPageIsEnd(isEnd: Boolean)
        fun setEndedGoalPageIsEnd(isEnd: Boolean)
        fun setEndedGoalList(list: ArrayList<MinimalGoalDetailContent>)
        fun setOngoingGoalListSizeOnTabLayout(totalElements: Int)
        fun setEndedGoalListSizeOnTabLayout(totalElements: Int)
        fun startAnimation()
        fun stopAnimation()
        fun setOthersJobInterest(jobInterest: ArrayList<String>)
    }

    interface Presenter {
        val view: View

        //fun getMemberJobInterest()
        fun onCleared()
        fun getOngoingGoalList(uid: String, page: Int)
        fun getEndedGoalList(uid: String, page: Int)
        fun getOthersJobInterest(uid: String)
    }
}