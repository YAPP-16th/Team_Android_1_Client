package com.eroom.erooja.feature.search.search_detail_frame

import androidx.databinding.ObservableField
import com.eroom.data.entity.GoalContent

interface SearchResultContract {
    interface View{
        fun setAllView(search: ArrayList<GoalContent>)
    }

    interface Presenter{
        var view: View
        fun getSearchJobInterest(interestId: Long?)
        fun getSearchGoalTitle(title: String?)
        fun onCleared()
    }
}
