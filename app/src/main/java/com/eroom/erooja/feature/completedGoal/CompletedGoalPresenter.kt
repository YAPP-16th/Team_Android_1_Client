package com.eroom.erooja.feature.completedGoal

import com.eroom.data.entity.UserSimpleData

class CompletedGoalPresenter(override var view: CompletedGoalContract.View)
    : CompletedGoalContract.Presenter {

    override fun getData(index: Int) {
        view.getAllView(UserSimpleData(4,"somebody", 99, "im shefm", "asdfe", " asdfeasg")
            .getUserDetailList(index))
    }
}