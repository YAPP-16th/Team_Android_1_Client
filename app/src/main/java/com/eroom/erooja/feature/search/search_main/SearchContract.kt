package com.eroom.erooja.feature.search.search_main

import com.eroom.data.entity.JobClass


interface SearchContract{
    interface View{
        fun setAlignedJobInterest(interest: MutableSet<String>)
        fun setUserJobInterest(interest: MutableSet<JobClass>)
    }

    interface Presenter{
        val view: View
        fun getAlignedJobInterest()
    }
}