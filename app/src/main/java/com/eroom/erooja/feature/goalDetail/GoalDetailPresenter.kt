package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.UserSimpleData

class GoalDetailPresenter(override var view: GoalDetailContract.View)
    :GoalDetailContract.Presenter{

    var tempdata = UserSimpleData(4,"somebody", 99, "im shefm", "asdfe", " asdfeasg")
        .getUserSimpleData()

    override fun getData() {
        view.getAllView(tempdata)

    }
}