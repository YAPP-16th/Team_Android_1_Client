package com.eroom.erooja.app.feature.search.search_main

import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass

interface SearchContract {
    interface View {
        fun setAlignedJobInterest(interest: MutableSet<String>)
        fun setUserJobInterest(interest: MutableSet<JobClass>)
        fun setAllView(search: ArrayList<GoalContent>)
        fun setIsEnd(boolean: Boolean)
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun getAlignedJobInterest()
        fun getSearchJobInterest(interestId: Long?, page: Int)
        fun onCleared()
    }
}