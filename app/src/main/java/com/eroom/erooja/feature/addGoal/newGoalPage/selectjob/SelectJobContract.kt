package com.eroom.erooja.feature.addGoal.newGoalPage.selectjob

import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse

interface SelectJobContract {
    interface View {
        fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>)
        fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>)
    }

    interface Presenter {
        val view: View
        fun getJobGroups()
        fun getJobGroupAndClasses(groupIds: List<Long>)
    }
}