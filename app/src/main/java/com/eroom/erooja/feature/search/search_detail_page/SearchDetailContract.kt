package com.eroom.erooja.feature.search.search_detail_page

interface SearchDetailContract {
    interface View {
        fun changeView(pos: Int)
    }

    interface Presenter {
        val view: View
    }
}