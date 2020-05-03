package com.eroom.erooja.feature.setting

interface SettingContract {
    interface View{
        fun setList(list: Array<String>)
    }

    interface Presenter{
        var view: View
        fun getSettingList(list: Array<String>)
    }
}