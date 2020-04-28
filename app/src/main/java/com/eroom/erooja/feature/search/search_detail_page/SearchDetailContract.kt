package com.eroom.erooja.feature.search.search_detail_page

import com.eroom.data.response.InterestedGoalsResponse

interface SearchDetailContract {
    interface View {
        fun changeView(pos: Int)
        fun updateSearchJobInterest(search: ArrayList<InterestedGoalsResponse>)
        fun updateSearchGoalTitle(search: ArrayList<InterestedGoalsResponse>)
    }
        interface Presenter {
            val view: View
            fun getSearchJobInterest(interestId: Long)
            fun getSearchGoalTitle(goalFilterBy:String, keyword: String)
            fun onCleared()
        }
    }
