package com.eroom.erooja.feature.participants_list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.Member
import com.eroom.domain.api.usecase.membergoal.GetParticipantedListUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class ParticipantsListPresenter(
    override val view: ParticipantsListContract.View,
    private val getParticipantedListUseCase: GetParticipantedListUseCase
) : ParticipantsListContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    val mParticipantDiffCallback = object : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return (oldItem.nickname == newItem.nickname) && (oldItem.uid == newItem.uid)
        }
    }

    @SuppressLint("CheckResult")
    override fun getParticipantedList(goalId: Long, page: Int) {
        view.startAnimation()
        getParticipantedListUseCase.getParticipantedList(goalId, size = 10, page = page)
            .subscribe({
                view.updateList(it.members, it.totalElement)
                view.updateIsEnd(it.totalPages - 1 <= page)
                view.stopAnimation()
            }, {
                Timber.e(it.localizedMessage)
                view.stopAnimation()
            })
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}