package com.eroom.erooja.feature.login

import com.kakao.usermgmt.response.MeV2Response
import java.util.concurrent.Future

interface LoginContract {
    interface View {
        val redirectSignUpActivity: (nickname: String?) -> Unit
        val redirectMainActivity: () -> Unit
    }

    interface Presenter {
        val view: View
        val requestMe: () -> Future<MeV2Response>
        fun onCleared()
    }
}