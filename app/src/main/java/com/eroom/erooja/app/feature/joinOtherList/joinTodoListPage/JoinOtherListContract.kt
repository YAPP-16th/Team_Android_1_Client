package com.eroom.erooja.app.feature.joinOtherList.joinTodoListPage

interface JoinOtherListContract {
    interface View {
        fun redirectNewGoalFinish(resultId: Long)
        fun failRequest()
        fun stopAnimation()
    }

    interface Presenter {
        var view: View

        fun addMyGoal(
            goalId: Long?,
            ownerUid: String?,
            endDt: String,
            todoList: ArrayList<String>
        )
    }
}