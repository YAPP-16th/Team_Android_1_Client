package com.eroom.erooja.feature.signup.kakao

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.eroom.data.localclass.DesignSelected
import com.eroom.data.localclass.DevelopSelected
import com.eroom.data.localclass.JobGroup
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityKakaoSignUpBinding
import com.eroom.erooja.feature.signup.page.JobClassFragment
import com.eroom.erooja.feature.signup.page.JobGroupFragment
import com.eroom.erooja.feature.signup.page.NicknameFragment


class KakaoSignUpActivity : AppCompatActivity(), KakaoSignUpContract.View {
    private lateinit var kakaoBinding: ActivityKakaoSignUpBinding
    private lateinit var presenter: KakaoSignUpPresenter

    private val mFragmentList = ArrayList<Fragment>()
    private var mPage = 0

    private var nicknameText = ""
    private lateinit var groupSelected: JobGroup
    private lateinit var devClassSelected: DevelopSelected
    private lateinit var designClassSelected: DesignSelected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        observeData()
    }

    private fun initPresenter() {
        presenter = KakaoSignUpPresenter(this)
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
        (mFragmentList[2] as JobClassFragment).mDevelopClassInfo.observe(this, Observer {
            devClassSelected = it
        })
        (mFragmentList[2] as JobClassFragment).mDesignClassInfo.observe(this, Observer {
            designClassSelected = it
        })
    }

    private fun initFragment() {
        mFragmentList.apply {
            addAll(listOf(
                NicknameFragment.newInstance()/*.apply { arguments = Bundle().apply { putString("key", "value") } }*/,
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
        if (mPage==2) (mFragmentList[mPage] as JobClassFragment).settingGroup(groupSelected)
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
        var index = 0
        var text = ""
        when (groupSelected) {
            JobGroup.DEVELOP -> devClassSelected.isSelected.forEach {
                if (it) text += "${devClassSelected.classEnum[index]}\n"
                index++
            }
            JobGroup.DESIGN -> designClassSelected.isSelected.forEach {
                if (it) text += "${designClassSelected.classEnum[index]}\n"
                index++
            }
        }
        this.toastLong("$nicknameText\n${groupSelected.name}\n${text}")
    }

    override fun onBackPressed() = prevButtonClicked()
}

