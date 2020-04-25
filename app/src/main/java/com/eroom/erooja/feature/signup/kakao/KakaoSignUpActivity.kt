package com.eroom.erooja.feature.signup.kakao

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eroom.data.localclass.JobGroup
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityKakaoSignUpBinding
import com.eroom.erooja.feature.signup.page.jobclass.JobClassFragment
import com.eroom.erooja.feature.signup.page.jobgroup.JobGroupFragment
import com.eroom.erooja.feature.signup.page.nickname.NicknameFragment
import com.eroom.erooja.feature.tab.TabActivity
import org.koin.android.ext.android.get

class KakaoSignUpActivity : AppCompatActivity(), KakaoSignUpContract.View {
    private lateinit var kakaoBinding: ActivityKakaoSignUpBinding
    private lateinit var presenter: KakaoSignUpPresenter

    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0

    private var nicknameText = ""
    private lateinit var groupSelected: JobGroup
    private var classSelected: ArrayList<Long> = ArrayList()
    private var groupId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
    }

    private fun initPresenter() {
        presenter = KakaoSignUpPresenter(this, get(), get(), get())
    }

    private fun setUpDataBinding() {
        kakaoBinding = DataBindingUtil.setContentView(this, R.layout.activity_kakao_sign_up)
        kakaoBinding.activity = this
    }

    private fun observeData() {
        (mFragmentList[0] as NicknameFragment).nickname.observe(this, Observer {
            nicknameText = it
        })
        (mFragmentList[1] as JobGroupFragment).jobGroup.observe(this, Observer {
            groupSelected = it
        })
        (mFragmentList[2] as JobClassFragment).mSelectedClassInfo.observe(this, Observer {
            classSelected = it
        })
        (mFragmentList[2] as JobClassFragment).mGroupId.observe(this, Observer {
            groupId = it
        })
    }

    private fun initFragment() {
        val getIntent = intent
        mFragmentList.apply {
            addAll(listOf(
                NicknameFragment.newInstance().apply { arguments = Bundle().apply { putString(Consts.NICKNAME, getIntent.getStringExtra(Consts.NICKNAME)) } },
                JobGroupFragment.newInstance(),
                JobClassFragment.newInstance()
            ))
        }.also {
            repeat(3) {index ->
                supportFragmentManager.beginTransaction().add(R.id.loginFrame, it[index]).hide(it[index]).commit()
            }
            supportFragmentManager.beginTransaction().show(it[0]).commit()
        }
    }

    fun nextButtonClicked() {
        mPage += 1
        if (mPage > 2) {
            finish()
            return
        }
        showFragment()
    }

    private fun hideFragment() = repeat(3) {
        supportFragmentManager.beginTransaction().hide(mFragmentList[it]).commit()
    }

    private fun showFragment() {
        hideFragment()
        supportFragmentManager.beginTransaction().show(mFragmentList[mPage]).commit()
        if (mPage==2) {
            (mFragmentList[mPage] as JobClassFragment).settingGroup(groupSelected)
            (mFragmentList[mPage] as JobClassFragment).settingSelectedId(classSelected)
        }
    }

    fun prevButtonClicked() {
        mPage -= 1
        if (mPage < 0) {
            finish()
            return
        }
        showFragment()
    }

    fun requestUserInfo() {
        presenter.requestUserInfo(nicknameText, classSelected)
    }

    override fun navigateToMain() {
        startActivity(
            Intent(this, TabActivity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            )
        )
    }

    override fun onBackPressed() = prevButtonClicked()

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}

