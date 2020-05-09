package com.eroom.erooja.feature.setting.setting_detail.setting_profile

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Environment
import com.eroom.domain.api.usecase.member.GetMemberProfileImagesUseCase
import com.eroom.domain.api.usecase.member.PutMemberProfileImagesUsecase
import okhttp3.MediaType
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File

class ProfilePresenter(override var view: ProfileContract.View,
                       private val getMemberProfileImages: GetMemberProfileImagesUseCase,
                       private val putMemberProfileImages: PutMemberProfileImagesUsecase)
    : ProfileContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getProfileImage() {
        getMemberProfileImages.getMemberProfileImages()
            .subscribe({
                if(it.isNotEmpty()) { view.setProfileImage(it[it.size - 1]) }
                else {  view.setProfileImage(null) }
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun updateProfileImage(string: String) {
        putMemberProfileImages.putMemberProfileImages(File(string))
            .subscribe({
                Timber.i("RESULT: ${Environment.getDataDirectory().toString() + string}")
            },{
                it.localizedMessage
            })
    }
}