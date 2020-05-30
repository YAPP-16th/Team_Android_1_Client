package com.eroom.erooja.app.feature.signup.kakao

interface KakaoSignUpContract {
    interface View {
        fun navigateToMain()
    }

    interface Presenter {
        val view: View
        fun requestUserInfo(nickname: String, ids: ArrayList<Long>)
        fun onCleared()
    }
}