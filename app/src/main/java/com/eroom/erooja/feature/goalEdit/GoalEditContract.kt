package com.eroom.erooja.feature.goalEdit

interface GoalEditContract {
    interface View {
        fun finishEdit()
        fun showMessage()
    }

    interface Presenter {
        val view: View
        fun requestEditGoal(goalId: Long, title: String, description: String)
    }
}