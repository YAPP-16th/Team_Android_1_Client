package com.eroom.erooja.feature.search.search_detail_frame

import com.eroom.data.entity.GoalContent

interface SearchResultContract {
    interface View {
        fun setAllView(search: ArrayList<GoalContent>)
        fun setIsEnd(boolean: Boolean)
    }

    interface Presenter {
        var view: View
        fun getSearchJobInterest(interestId: Long?, page: Int)
        fun getSearchGoalTitle(title: String?, page: Int)
        fun onCleared()
    }
}
