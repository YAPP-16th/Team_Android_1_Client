package com.eroom.erooja.feature.main


import android.content.Context
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import com.eroom.domain.utils.fromHtml

import com.eroom.erooja.databinding.FragmentMainBinding
import com.eroom.erooja.feature.tab.TabActivity
import org.koin.android.ext.android.get

class MainFragment : Fragment(), MainContract.View {
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var presenter: MainPresenter

    val nicknameText: ObservableField<String> = ObservableField()
    val randomJobText: ObservableField<String> = ObservableField()

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MainPresenter(this, get(), get(), get())
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

    override fun setNickname(nickname: String) = nicknameText.set("$nickname 님의 관심직무")

    override fun setJobInterestInfo(randomJob: String, randomJobId: Long, size: Int) {
        randomJobText.set(randomJob)
        mainBinding.userInterestInfoCount.text = fromHtml("외 <u>${size - 1}개</u>")
    }
}
