package com.eroom.erooja.feature.filter

import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse

interface FilterContract {
    interface View {
        fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>)
        fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>)
        //fun setMemberJobInterest(interest: MutableSet<Int>)
    }
    interface Presenter {
        val view: View
        fun getJobGroups()
        fun getJobGroupAndClasses(groupIds: List<Long>)
        fun onCleared()
    }
}