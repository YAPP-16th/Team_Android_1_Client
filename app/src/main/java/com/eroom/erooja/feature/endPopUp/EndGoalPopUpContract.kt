package com.eroom.erooja.feature.endPopUp

interface EndGoalPopUpContract {
    interface View {
        fun setView(achieveRate: Int)
        fun navigateToMainPage()
        fun initView()
        fun navigateToSearchGoal()
    }

    interface Presenter {
        var view: View
        fun getData()
    }
}