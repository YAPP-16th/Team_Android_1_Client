package com.eroom.erooja.feature.search.search_detail_page

interface SearchDetailContract {
    interface View {
        fun changeView(pos: Int)
        fun checkContentSize(size: Int)
    }

    interface Presenter{
        var view: View
        fun getSearchJobInterest(interestId: Long?)
        fun getSearchGoalTitle(title: String?)
        fun onCleared()
    }
}