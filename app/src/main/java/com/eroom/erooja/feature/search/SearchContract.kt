package com.eroom.erooja.feature.search

interface SearchContract {
    interface View {

    }

    interface Presenter {
        val view: View
    }
}