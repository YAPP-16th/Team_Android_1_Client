package com.eroom.erooja.feature.setting

import com.eroom.data.entity.JobClass

interface SettingContract {
    interface View{
        fun setList(list: Array<String>)
        fun setUserJobInterest(interest: MutableSet<JobClass>)
        fun logoutCompleted()
        fun InformUpdatedMsg()

    }

    interface Presenter{
        var view: View
        fun getSettingList(list: Array<String>)
        fun updateJobInterest(original: ArrayList<Long>, updated: ArrayList<Long>)
        fun getAlignedJobInterest()
        fun logout()
        fun onCleared()

    }
}