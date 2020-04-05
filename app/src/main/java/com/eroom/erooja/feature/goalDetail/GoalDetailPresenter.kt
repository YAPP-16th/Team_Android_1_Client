package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.UserSimpleData

class GoalDetailPresenter(override var view: GoalDetailContract.View)
    :GoalDetailContract.Presenter{

    var tempdata = UserSimpleData()

    override fun getData() {
        view.getAllView(tempdata)

    }
}