package com.eroom.erooja.feature.setting.setting_help

class HelpPresenter(override var view: HelpContract.View) : HelpContract.Presenter {
    override fun getArrayList(question: Array<String>, answer: Array<String>) {
        view.setRecycler(question, answer)
    }
}