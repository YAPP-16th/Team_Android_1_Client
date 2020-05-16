package com.eroom.erooja.feature.addDirectList.addMyTodoListPage

interface AddMyListContract {
    interface View {
        fun redirectNewGoalFinish(resultId: Long)
        fun failRequest()
    }

    interface Presenter {
        val view: View

        fun addMyGoal(
            goalId: Long?,
            ownerUid: String?,
            endDt: String,
            todoList: ArrayList<String>
        )
        fun onCleared()

    }
}