package com.eroom.erooja.app.feature.addDirectList.inactivejob

import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse

interface InactiveJobContract {
    interface View {
        fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>)
        fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>)
        fun setJobInterestOfTodoList(jobInterestList: List<Long>)
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun getJobGroups()
        fun getJobGroupAndClasses(groupIds: List<Long>)
        fun getJobInterestOfTodoList(goalId: Long?)
        fun onCleared()
    }
}