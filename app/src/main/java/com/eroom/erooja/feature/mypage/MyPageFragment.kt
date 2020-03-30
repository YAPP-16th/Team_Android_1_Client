package com.eroom.erooja.feature.mypage


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentMyPageBinding

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment(), MyPageContract.View {
    private lateinit var myPageBinding: FragmentMyPageBinding
    private lateinit var presenter: MyPagePresenter

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MyPagePresenter(this)
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

    }

}
