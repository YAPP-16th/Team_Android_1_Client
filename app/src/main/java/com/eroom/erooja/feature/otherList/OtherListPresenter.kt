package com.eroom.erooja.feature.otherList

import com.eroom.data.entity.UserSimpleData

class OtherListPresenter(override var view: OtherListContract.View)
    : OtherListContract.Presenter {

    override fun getData(index: Int) {
        view.getAllView(UserSimpleData(4,"somebody", 99, "im shefm", "asdfe", " asdfeasg")
            .getUserDetailList(index))
    }
}