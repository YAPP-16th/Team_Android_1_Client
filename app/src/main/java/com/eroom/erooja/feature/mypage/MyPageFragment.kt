package com.eroom.erooja.feature.mypage


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import org.koin.android.ext.android.get

import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentMyPageBinding
import org.koin.core.context.KoinContextHandler.get

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment(), MyPageContract.View {
    private lateinit var myPageBinding: FragmentMyPageBinding
    private lateinit var presenter: MyPagePresenter
    val nicknameText: ObservableField<String> = ObservableField()

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MyPagePresenter(this, get(), get(), get())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return myPageBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        myPageBinding = FragmentMyPageBinding.inflate(inflater, container, false)
        myPageBinding.fragment = this
    }

    private fun initView() {
        if (presenter.isGuest()) {
            nicknameText.set("게스트 님")
        } else {
            presenter.getUserInfo()
            //presenter.getMemberJobInterest()
        }
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    override fun setNickname(nickname: String) {
        nicknameText.set("$nickname 님")
    }

    override fun setJobInterestInfo(randomJob: String, randomJobId: Long, size: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
