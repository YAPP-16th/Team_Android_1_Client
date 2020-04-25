package com.eroom.erooja.feature.tab

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.job.GetJobGroupAndClassUseCase
import com.eroom.domain.api.usecase.job.GetJobGroupUseCase
import com.eroom.domain.utils.addTo
import com.eroom.erooja.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class TabPresenter(override val view: TabContract.View,
                   private val getJobGroupUseCase: GetJobGroupUseCase,
                   private val getJobGroupAndClassUseCase: GetJobGroupAndClassUseCase
) : TabContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_tab_main -> {
                view.loadFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_search -> {
                view.loadFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_my_page -> {
                view.loadFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    @SuppressLint("CheckResult")
    override fun getJobGroups() {
        getJobGroupUseCase.getJobGroup()
            .subscribe({
                view.reRequestClassByGroup(it)
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    @SuppressLint("CheckResult")
    override fun getJobGroupAndClasses(groupIds: List<Long>) {
        Observable.fromIterable(groupIds)
            .flatMap { getJobGroupAndClassUseCase.getJobGroupAndClass(it) }
            .map {
                it
            }.toList()
            .subscribe({
                view.updateJobGroupAndClass(it) //사용자의 직무, 직군 불러옴
            },{
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}