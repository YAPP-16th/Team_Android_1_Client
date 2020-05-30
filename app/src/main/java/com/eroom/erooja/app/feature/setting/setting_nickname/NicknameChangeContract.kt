package com.eroom.erooja.app.feature.setting.setting_nickname

interface NicknameChangeContract {
    interface View {
        fun setMyNickname(nickname: String)
        fun nicknameDuplicationError()
        fun nicknameDuplicationPass()
    }

    interface Presenter {
        var view: View
        fun updateNickname(nickname: String)
        fun checkNickname(nickname: String)
        fun getMyNickname()
        fun onCleared()
    }
}