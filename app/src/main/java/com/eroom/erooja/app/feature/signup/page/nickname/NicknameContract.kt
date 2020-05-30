package com.eroom.erooja.app.feature.signup.page.nickname

interface NicknameContract {
    interface View {
        fun showCheckImage()
        fun hideCheckImage()
        fun showErrorImage()
        fun hideErrorImage()
        fun setDuplicatedNickname()
        fun unsetDuplicatedNickname()
        fun setValidatedNickname()
        fun unsetValidatedNickname()
    }

    interface Presenter {
        val view: View
        fun checkNickname(nickname: String)
        fun onCleared()
    }
}