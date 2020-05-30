package com.eroom.erooja.app.feature.goalEdit

interface GoalEditContract {
    interface View {
        fun finishEdit()
        fun showMessage()
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun requestEditGoal(goalId: Long, title: String, description: String)
    }
}