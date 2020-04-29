package com.eroom.erooja.feature.main

import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass

interface MainContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(randomJob: String, randomJobId: Long, classList: ArrayList<JobClass>)
        fun setParticipatedList()
        fun setNewGoalBrowse(content: ArrayList<GoalContent>)
    }

    interface Presenter {
        val view: View
        fun getUserInfo()
        fun getMemberJobInterest()
        fun onCleared()
        fun getInterestedGoals(interestId: Long)
        fun getMyParticipatedList(uid: String)
    }
}