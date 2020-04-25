package com.eroom.erooja.feature.main


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.fromHtml
import com.eroom.domain.utils.toastShort

import com.eroom.erooja.databinding.FragmentMainBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.feature.addGoal.newGoalFrame.NewGoalActivity
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.tab.TabActivity
import org.koin.android.ext.android.get

class MainFragment : Fragment(), MainContract.View {
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var presenter: MainPresenter

    private lateinit var mNewGoalAdapter: NewGoalBrowseAdapter

    val nicknameText: ObservableField<String> = ObservableField()
    val randomJobText: ObservableField<String> = ObservableField()

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MainPresenter(this, get(), get(), get(), get())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return mainBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        mainBinding.fragment = this
    }

    private fun initView() {
        if (presenter.isGuest()) {
            nicknameText.set("게스트 님의 관심직무")
        } else {
            presenter.getUserInfo()
            presenter.getMemberJobInterest()
        }
    }

    fun navigateToSearchTab() = (activity as TabActivity).changeTabToSearch()

    fun navigateToMyPageTab() = (activity as TabActivity).changeTabToMyPage()

    fun navigateToAddGoal() = (activity as TabActivity).navigateToNewGoal()

    fun navigateToSearchActivity() = startActivity(Intent(activity, SearchDetailActivity::class.java))

    override fun setNickname(nickname: String) = nicknameText.set("$nickname 님의 관심직무")

    override fun setJobInterestInfo(randomJob: String, randomJobId: Long, classList: ArrayList<JobClass>) {
        randomJobText.set(randomJob)
        var contentString = ""
        for (classItem in classList) {
            contentString += classItem.name + ", "
        }
        mainBinding.userInterestInfoCount.setOnClickListener {
            startActivity(Intent(activity, EroojaDialogActivity::class.java).apply {
                putExtra(Consts.DIALOG_TITLE, "관심직무")
                putExtra(Consts.DIALOG_CONTENT, contentString)
            })
        }
        presenter.getInterestedGoals(randomJobId)
    }

    override fun setNewGoalBrowse(content: ArrayList<GoalContent>) {
        mNewGoalAdapter = NewGoalBrowseAdapter(content, randomJobText.get() ?: "")
        mainBinding.newGoalRecycler.apply {
            adapter = mNewGoalAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
