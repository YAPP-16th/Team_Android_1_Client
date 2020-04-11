package com.eroom.erooja.feature.search.searchpage

interface SearchContract {
    interface View {
        fun changeView(pos : Int)
    }

    interface Presenter {
        val view: View
    }
}