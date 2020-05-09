package com.eroom.erooja.feature.setting.setting_help

interface HelpContract {
    interface View{
        fun setRecycler(question: Array<String>, answer: Array<String>)
    }

    interface Presenter{
        var view:View
        fun getArrayList(question: Array<String>, answer: Array<String>)
    }
}