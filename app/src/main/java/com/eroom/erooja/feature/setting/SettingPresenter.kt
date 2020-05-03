package com.eroom.erooja.feature.setting


class SettingPresenter(override var view: SettingContract.View)
    :SettingContract.Presenter {

    override fun getSettingList(list: Array<String>) {
        view.setList(list)
    }
}