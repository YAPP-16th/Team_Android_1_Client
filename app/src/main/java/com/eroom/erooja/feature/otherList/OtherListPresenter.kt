package com.eroom.erooja.feature.otherList

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetMemberProfileImagesUseCase
import com.eroom.domain.api.usecase.member.PostMemberInfoUseCase
import com.eroom.domain.api.usecase.member.PostOtherMemberInfoUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import timber.log.Timber

class OtherListPresenter(override var view: OtherListContract.View,
                         var getTodoListUseCase: GetTodoListUseCase,
                         var postOtherMemberInfoUseCase: PostOtherMemberInfoUseCase
)
    : OtherListContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getData(uid: String, goalId: Long) {
        view.startAnimation()
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                view.setAllView(it.content)
                view.stopAnimation()
            },{
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            })
    }

    @SuppressLint("CheckResult")
    override fun getProfileImage(uid: String) {
        postOtherMemberInfoUseCase.postMemberInfo(uid)
            .subscribe({
                if (!it.imagePath.isNullOrEmpty()) view.setProfileImage(it.imagePath)
            },{
                Timber.e(it.localizedMessage)
            })
    }
}