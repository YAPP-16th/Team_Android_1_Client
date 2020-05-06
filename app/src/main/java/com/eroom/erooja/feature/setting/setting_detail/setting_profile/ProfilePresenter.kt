package com.eroom.erooja.feature.setting.setting_detail.setting_profile

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetMemberProfileImages
import timber.log.Timber

class ProfilePresenter(override var view: ProfileContract.View,
                       private val getMemberProfileImages: GetMemberProfileImages)
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
}