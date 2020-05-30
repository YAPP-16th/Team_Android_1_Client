package com.eroom.erooja.app.feature.addDirectList.addMyTodoListPage

interface AddMyListContract {
    interface View {
        fun redirectNewGoalFinish(resultId: Long)
        fun failRequest()
        fun stopAnimation()
    }

    interface Presenter {
        val view: View
        fun addMyGoal(
            goalId: Long?,
            ownerUid: String?,
            endDt: String,
            todoList: ArrayList<String>
        )
        fun reparticipateToMyEndedGoal(goalId: Long, endDt: String)
        fun onCleared()
    }
}