package com.eroom.erooja.feature.ongoingGoal

import com.eroom.data.entity.UserSimpleData

class OngoingGoalPresenter(override var view: OngoingGoalContract.View)
    : OngoingGoalContract.Presenter {
    var tempdata = UserSimpleData(4,"somebody", 99, "im shefm", "asdfe", " asdfeasg")
        .getUserSimpleData()

    override fun getData() {
        view.setAllView(tempdata)

    }
}