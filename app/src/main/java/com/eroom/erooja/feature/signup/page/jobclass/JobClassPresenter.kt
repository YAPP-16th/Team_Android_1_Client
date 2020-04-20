package com.eroom.erooja.feature.signup.page.jobclass

import android.annotation.SuppressLint
import com.eroom.data.localclass.JobGroup
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import timber.log.Timber

class JobClassPresenter(
    override val view: JobClassContract.View,
    private val getJobGroupUseCase: GetJobGroupUseCase,
    private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : JobClassContract.Presenter {

    @SuppressLint("CheckResult")
    override fun getJobGroups(jobGroup: JobGroup) {
        getJobGroupUseCase.getJobGroup()
            .subscribe({
                for (existGroup in it) {
                    if (existGroup.name == jobGroup.groupName)
                        getJobGroupAndClasses(existGroup.id)
                }
            },{
                Timber.e(it.localizedMessage)
            })
    }

    @SuppressLint("CheckResult")
    override fun getJobGroupAndClasses(groupId: Long) {
        getJobGroupAndClassUseCase.getJobGroupAndClass(groupId)
            .subscribe({
                view.settingGroupView(it.jobInterests)
            },{
                Timber.e(it.localizedMessage)
            })
    }


}