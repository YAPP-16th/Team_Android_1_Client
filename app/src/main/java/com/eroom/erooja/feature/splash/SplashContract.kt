package com.eroom.erooja.feature.splash

import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse

interface SplashContract {
    interface View {
        fun animation()
        fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>)
        fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>)
        fun navigateToLogin()
        fun navigateToOnBoarding()
        fun navigateToMain()
        fun navigateToSignUp()
    }

    interface Presenter {
        val view: View
        fun getJobGroups()
        fun getJobGroupAndClasses(groupIds: List<Long>)
        fun initDelay()
        fun onCleared()
    }
}