package com.eroom.erooja.feature.setting.setting_nickname

interface NicknameChangeContract{
    interface View{
        fun showCheckImage()
        fun hideCheckImage()
        fun showErrorImage()
        fun hideErrorImage()
        fun setDuplicatedNickname()
        fun unsetDuplicatedNickname()
        fun setValidatedNickname()
        fun unsetValidatedNickname()
    }

    interface Presenter{
        var view: View

        fun checkNickname(nickname: String)
        fun onCleared()
    }
}