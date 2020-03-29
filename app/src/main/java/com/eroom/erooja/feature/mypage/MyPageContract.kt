package com.eroom.erooja.feature.mypage

interface MyPageContract {
    interface View {

    }

    interface Presenter {
        val view: View
    }
}