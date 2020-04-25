package com.eroom.erooja.feature.search.search_detail_page

import com.eroom.data.response.SearchGoalResponse

interface SearchDetailContract {
    interface View {
        fun changeView(pos: Int)
        fun searchGoal(search: ArrayList<SearchGoalResponse>)
    }

    interface Presenter {
        val view: View

        fun getUserGoal(jobInterestIds: Long)
    }
}