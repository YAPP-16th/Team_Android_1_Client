package com.eroom.erooja.feature.search.search_detail_page

import com.eroom.data.entity.GoalContent

interface SearchDetailContract {
    interface View {
        fun changeView(pos: Int)
        fun checkContentSize(size: Int)
       // fun checkContentTitleSize(title: ArrayList<GoalContent>)
    }

    interface Presenter{
        var view: View
        fun getSearchJobInterest(interestId: Long?)
        fun getSearchGoalTitle(title: String?)
        fun onCleared()
    }
}