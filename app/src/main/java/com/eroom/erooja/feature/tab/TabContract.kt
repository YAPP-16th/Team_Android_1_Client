package com.eroom.erooja.feature.tab

import androidx.fragment.app.Fragment
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.google.android.material.bottomnavigation.BottomNavigationView

interface TabContract {
    interface View{
        fun loadFragment(index: Int): Fragment
        fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>)
        fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>)
    }

    interface Presenter {
        val view: View
        val listener: BottomNavigationView.OnNavigationItemSelectedListener
        fun getJobGroups()
        fun getJobGroupAndClasses(groupIds: List<Long>)
        fun onCleared()
    }
}