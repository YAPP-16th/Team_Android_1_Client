package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.MinimalTodoListContent
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.data.response.GoalDetailResponse

interface GoalDetailContract {
    interface View {
        fun setInterestedClassName(list: List<String>)
        fun setRecyclerView(
            todoList: ArrayList<MinimalTodoListContent>,
            isMine: Boolean,
            isJoined: Boolean
        )

        fun setTodoList(todoList: ArrayList<MinimalTodoListDetail>)
        fun stopAnimation()
        fun setInitialView(list: GoalDetailResponse)
        fun setIsExistedInMyPage(isExistedInMyPage: Boolean)
    }

    interface Presenter {
        var view: View
        fun getData(goalId: Long)
        fun getUserTodoList(uid: String, goalId: Long)
        fun getInterestedClassName(interestedIds: List<Long>)
        fun getMinimalTodoList(goalId: Long)
        fun onCleared()
    }
}