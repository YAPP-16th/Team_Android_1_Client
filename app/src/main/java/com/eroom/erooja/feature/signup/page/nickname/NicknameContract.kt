package com.eroom.erooja.feature.signup.page.nickname

interface NicknameContract {
    interface View {
        fun showCheckImage()
        fun hideCheckImage()
        fun showErrorImage()
        fun hideErrorImage()
        fun setValidatedNickname()
        fun unsetValidatedNickname()
    }

    interface Presenter {
        val view: View
    }
}