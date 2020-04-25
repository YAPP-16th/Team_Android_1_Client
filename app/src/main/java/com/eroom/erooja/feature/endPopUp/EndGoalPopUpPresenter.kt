package com.eroom.erooja.feature.endPopUp

class EndGoalPopUpPresenter(override var view: EndGoalPopUpContract.View) :
    EndGoalPopUpContract.Presenter {

    override fun getData() {
        view.setView(90)
    }

}