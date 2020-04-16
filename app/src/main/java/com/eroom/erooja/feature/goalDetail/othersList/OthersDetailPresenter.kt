package com.eroom.erooja.feature.goalDetail.othersList

import com.eroom.data.entity.UserSimpleData

class OthersDetailPresenter(override var view: OthersDetailContract.View)
    : OthersDetailContract.Presenter {

    override fun getData(index: Int) {
        view.getAllView(UserSimpleData(4,"somebody", 99, "im shefm", "asdfe", " asdfeasg")
            .getUserDetailList(index))
    }
}