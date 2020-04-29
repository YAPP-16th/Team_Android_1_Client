package com.eroom.erooja.feature.goalDetail

interface GoalDetailContract {
    interface View{
        fun setView(title: String, description: String, joinCount: Int, startDate: String, endDate: String)
        fun setInterestedClassName(list: List<String>)
    }

    interface Presenter{
        var view:View
        fun getData(goalId: Long)
        fun getInterestedClassName(interestedIds: List<Long>)
        fun onCleared()
    }
}