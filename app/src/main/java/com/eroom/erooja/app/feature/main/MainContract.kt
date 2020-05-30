package com.eroom.erooja.app.feature.main

import com.eroom.data.entity.AlarmContent
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.MinimalGoalDetailContent

interface MainContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(randomJob: String, randomJobId: Long, classList: ArrayList<JobClass>)
        fun setParticipatedList(list: ArrayList<MinimalGoalDetailContent>)
        fun saveUid(uid: String)
        fun setNewGoalBrowse(content: ArrayList<GoalContent>)
        fun setUnReadNotification()
        fun showEndPopUp(list: ArrayList<AlarmContent>)
        fun startAnimation()
        fun startBlockAnimation()
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun getUserInfo()
        fun getMemberJobInterest()
        fun onCleared()
        fun getInterestedGoals(interestId: Long, uid: String)
        fun getMyParticipatedList(uid: String)
        fun getNotificationInfo()
    }
}