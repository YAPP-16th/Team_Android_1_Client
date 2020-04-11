package com.eroom.erooja.feature.goalDetail.othersList

import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.feature.goalDetail.GoalDetailContract

class OthersDetailPresenter(override var view: OthersDetailContract.View)
    : OthersDetailContract.Presenter {

    var tempdata = UserSimpleData()

    override fun getData() {
        view.getAllView(tempdata)
    }
}