package com.eroom.erooja.feature.otherList

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetMemberProfileImagesUseCase
import com.eroom.domain.api.usecase.member.PostMemberInfoUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import timber.log.Timber

class OtherListPresenter(override var view: OtherListContract.View,
                         var getTodoListUseCase: GetTodoListUseCase,
                         var postMemberInfoUseCase: PostMemberInfoUseCase
)
    : OtherListContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getData(uid: String, goalId: Long){
        getTodoListUseCase.getUserTodoList(uid, goalId)
            .subscribe({
                view.setAllView(it.content)
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun getProfileImage(uid: String) {
        postMemberInfoUseCase.postMemberInfo(uid)
            .subscribe({
                if (!it.imagePath.isNullOrEmpty()) view.setProfileImage(it.imagePath)
            },{
                Timber.e(it.localizedMessage)
            })
    }
}