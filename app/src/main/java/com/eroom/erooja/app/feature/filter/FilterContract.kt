package com.eroom.erooja.app.feature.filter

import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse

interface FilterContract {
    interface View {
        fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>)
        fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>)
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun getJobGroups()
        fun getJobGroupAndClasses(groupIds: List<Long>)
        fun onCleared()
    }
}