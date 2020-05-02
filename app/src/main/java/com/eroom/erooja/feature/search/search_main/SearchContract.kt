package com.eroom.erooja.feature.search.search_main

import androidx.fragment.app.Fragment
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.erooja.feature.search.search_detail_frame.SearchResultFragment

interface SearchContract{
    interface View{
       // fun loadFragment(index: Int): Fragment

        fun setAlignedJobInterest(interest: MutableSet<String>)
        fun setUserJobInterest(interest: MutableSet<JobClass>)
        //fun setAllView(search: ArrayList<GoalContent>)
        fun checkContentSize(size: Int)


    }

    interface Presenter{
        val view: View
        fun getAlignedJobInterest()
        fun getSearchJobInterest(interestId: Long?)
        fun onCleared()
    }
}