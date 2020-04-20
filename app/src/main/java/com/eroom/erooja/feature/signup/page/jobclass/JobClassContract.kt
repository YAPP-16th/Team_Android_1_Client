package com.eroom.erooja.feature.signup.page.jobclass

import com.eroom.data.entity.JobClass
import com.eroom.data.localclass.JobGroup

interface JobClassContract {
    interface View {
        fun settingGroupView(jobClasses: ArrayList<JobClass>)
        fun settingGroupId(id: Long)
    }

    interface Presenter {
        val view: View
        fun getJobGroups(jobGroup: JobGroup)
        fun getJobGroupAndClasses(groupId: Long)
    }
}