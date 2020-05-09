package com.eroom.erooja.feature.otherList

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetMemberProfileImagesUseCase
import com.eroom.domain.api.usecase.todo.GetTodoListUseCase
import timber.log.Timber

class OtherListPresenter(override var view: OtherListContract.View,
                         var getTodoListUseCase: GetTodoListUseCase,
                         var getMemberProfileImages: GetMemberProfileImagesUseCase
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