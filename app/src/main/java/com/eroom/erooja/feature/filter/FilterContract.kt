package com.eroom.erooja.feature.filter

interface FilterContract {
    interface View {

    }
    interface Presenter {
        val view: View
    }
}