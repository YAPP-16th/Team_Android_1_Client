package com.eroom.erooja.feature.endPopUp

interface EndGoalPopUpContract {
    interface View {
        fun setView(goalTitle: String, achieveRate: Int)
        fun navigateToMainPage()
        fun initView()
        fun navigateToSearchGoal()
    }

    interface Presenter {
        var view: View
        fun getData()
    }
}