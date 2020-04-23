package com.eroom.erooja.feature.search.search_main

import android.annotation.SuppressLint
import com.eroom.data.localclass.DesignClass
import com.eroom.data.localclass.DevelopClass
import com.eroom.domain.api.usecase.member.GetMemberJobInterestsUseCase
import timber.log.Timber

class SearchPresenter(override val view:SearchContract.View,
                      private val  getMemberJobInterestsUseCase: GetMemberJobInterestsUseCase
): SearchContract.Presenter{

    @SuppressLint("CheckResult")
    override fun getAlignedJobInterest() {
        getMemberJobInterestsUseCase.getMemberJobInterests()
            .subscribe({
                val userJobInterest = mutableSetOf<String>()
                var jopGroupId = it[0].id.toInt() // 1 or 2
                for (group in it) {
                    group.jobInterests.map { jobclass ->
                        userJobInterest.add(jobclass.name)
                    }

                    when (jopGroupId) {
                        1 -> {
                            repeat(DevelopClass.getArray().size) {
                                userJobInterest.add(DevelopClass.getArray()[it].getName())
                            }
                            repeat(DesignClass.getArray().size) {
                                userJobInterest.add(DesignClass.getArray()[it].getName())
                            }
                        }
                        else -> {
                            repeat(DesignClass.getArray().size) {
                                userJobInterest.add(DesignClass.getArray()[it].getName())
                            }
                            repeat(DevelopClass.getArray().size) {
                                userJobInterest.add(DevelopClass.getArray()[it].getName())
                            }
                        }
                    }
                }
                view.setAlignedJobInterest(userJobInterest)
            },{
                Timber.i(it.localizedMessage)
            })

    }
}