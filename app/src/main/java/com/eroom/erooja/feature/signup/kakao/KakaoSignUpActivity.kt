package com.eroom.erooja.feature.signup.kakao

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.usermgmt.response.model.UserAccount
import com.kakao.util.OptionalBoolean
import timber.log.Timber


class KakaoSignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_sign_up)


    }



}
