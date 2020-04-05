package com.eroom.erooja.feature.signup.page


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData

import com.eroom.erooja.databinding.FragmentNicknameBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import com.jakewharton.rxbinding.widget.RxTextView
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class NicknameFragment : Fragment() {
    private lateinit var nicknameBinding: FragmentNicknameBinding
    val nickname: MutableLiveData<String> = MutableLiveData()
    var nicknameCheck: ObservableField<Boolean> = ObservableField(false)

    companion object {
        @JvmStatic
        fun newInstance() = NicknameFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return nicknameBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        nicknameBinding = FragmentNicknameBinding.inflate(inflater, container, false)
        nicknameBinding.fragment = this
    }

    private fun initView() {
        RxTextView.textChanges(nicknameBinding.nicknameText)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                nickname.value = it
                nicknameBinding.nicknameLengthError.visibility = if (it.length < 2) View.VISIBLE else View.INVISIBLE
                if (it.length >= 2) {
                    nicknameBinding.nicknameDuplicatedCheck.visibility = View.VISIBLE
                    nicknameCheck.set(true)
                } else {
                    nicknameBinding.nicknameDuplicatedCheck.visibility = View.GONE
                    nicknameCheck.set(false)
                }
            }
    }

    fun nextButtonClicked() {
        nicknameCheck.get()?.let {
            if (it) (activity as KakaoSignUpActivity).nextButtonClicked()
        }
    }

}
