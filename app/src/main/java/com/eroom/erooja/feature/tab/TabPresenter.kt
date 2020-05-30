package com.eroom.erooja.feature.tab

import android.annotation.SuppressLint
import com.eroom.domain.api.usecase.member.GetMemberInfoUseCase
import com.eroom.domain.utils.addTo
import com.eroom.erooja.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class TabPresenter(
    override val view: TabContract.View,
    private val getMemberInfoUseCase: GetMemberInfoUseCase
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
    override fun getUserInfo() {
        getMemberInfoUseCase.getUserInfo()
            .subscribe({
                view.setUserInfo(it.uid, it.nickname, it.imagePath)
            }, {
                Timber.e(it.localizedMessage)
            }) addTo compositeDisposable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}