package com.eroom.erooja.feature.tab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.eroom.data.entity.JobClass
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.customview.parcelizeclass.ParcelizeAlarmContent
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityTabBinding
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalActivity
import com.eroom.erooja.feature.endPopUp.EndGoalPopUpActivity
import com.eroom.erooja.feature.main.MainFragment
import com.eroom.erooja.feature.mypage.MyPageFragment
import com.eroom.erooja.feature.search.search_main.SearchFragment
import com.eroom.erooja.feature.setting.SettingFragment
import com.eroom.erooja.singleton.JobClassHashMap.hashmap
import com.eroom.erooja.singleton.UserInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.get

class TabActivity : AppCompatActivity(), TabContract.View {
    private lateinit var mainBinding: ActivityTabBinding
    private lateinit var presenter: TabPresenter

    private val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0
    val alarmCallBack: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var listener: BottomNavigationView.OnNavigationItemSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
        saveUserInfo()
    }

    private fun initPresenter() {
        presenter = TabPresenter(this, get())
        listener = presenter.listener
    }

    private fun setUpDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_tab)
        mainBinding.activity = this
    }

    private fun saveUserInfo() {
        presenter.getUserInfo()
    }

    override fun setUserInfo(uid: String, nickname: String, imagePath: String?) {
        UserInfo.myUId = uid
        UserInfo.myNickname = nickname
        imagePath?.let { UserInfo.myImagePath = it }
    }

    private fun initFragment() =
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance()).commit()


    override fun loadFragment(index: Int) =
        when (index) {
            0 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, SearchFragment.newInstance()).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MyPageFragment.newInstance()).commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, SettingFragment.newInstance()).commit()
            }
            else -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
        }

    fun changeTabToSearch() {
        mainBinding.mainBottomTab.selectedItemId = R.id.bottom_tab_search
    }

    fun changeTabToMyPage() {
        mainBinding.mainBottomTab.selectedItemId = R.id.bottom_tab_my_page
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        }
        else {
            backPressedTime = tempTime
            this.toastShort(resources.getString(R.string.back_button_click))
        }
    }

    fun navigateToNewGoal(uId: String, classList: ArrayList<JobClass>) = startActivity(Intent(this, NewGoalActivity::class.java).apply {
        putExtra(Consts.UID, uId)
        putExtra(Consts.INTERESTED_JOB_CLASS, classList.map { it.id }.toLongArray())
    })

    fun replaceFragment(index: Int) {
        loadFragment(index)
    }

    fun navigateToPopUp(list: ArrayList<ParcelizeAlarmContent>) {
        startActivityForResult(Intent(this, EndGoalPopUpActivity::class.java).apply {
            putExtra(Consts.POP_UP_LIST, list)
        }, 3000)
        overridePendingTransition(R.anim.slide_up, R.anim.hold)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3000 && resultCode == 4000) {
            data?.let {
                alarmCallBack.value = it.getBooleanExtra(Consts.ALL_READ, false)
            }
        }
    }

    fun startBlockAnimation() {
        mainBinding.colorLoading.visibility = View.GONE
        mainBinding.blockView.visibility = View.VISIBLE
        mainBinding.whiteLoading.visibility = View.VISIBLE
        mainBinding.colorLoading.cancelAnimation()
        mainBinding.whiteLoading.playAnimation()
    }

    fun startAnimation() {
        mainBinding.blockView.visibility = View.GONE
        mainBinding.whiteLoading.visibility = View.GONE
        mainBinding.colorLoading.visibility = View.VISIBLE
        mainBinding.whiteLoading.cancelAnimation()
        mainBinding.colorLoading.playAnimation()
    }

    fun stopAnimation() {
        mainBinding.blockView.visibility = View.GONE
        mainBinding.whiteLoading.visibility = View.GONE
        mainBinding.colorLoading.visibility = View.GONE
        mainBinding.whiteLoading.cancelAnimation()
        mainBinding.colorLoading.cancelAnimation()
    }
}
