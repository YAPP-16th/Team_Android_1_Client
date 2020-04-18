package com.eroom.erooja.feature.signup.page.nickname


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
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class NicknameFragment : Fragment(), NicknameContract.View {
    private lateinit var nicknameBinding: FragmentNicknameBinding
    private lateinit var presenter: NicknamePresenter

    val nickname: MutableLiveData<String> = MutableLiveData()
    var nicknameCheck: ObservableField<Boolean> = ObservableField(false)

    companion object {
        @JvmStatic
        fun newInstance() =
            NicknameFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initPresenter()
        setUpDataBinding(inflater, container)
        initView()
        return nicknameBinding.root
    }

    private fun initPresenter() {
        presenter = NicknamePresenter(this, get())
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
                nicknameBinding.nicknameLengthError.visibility = if (!it.contains(" ") && it.length in 1..1) View.VISIBLE else View.INVISIBLE
                if (!it.contains(" ") && it.length >= 2) {
                    presenter.checkNickname(it)
                }
            }
    }

    fun nextButtonClicked() {
        nicknameCheck.get()?.let {
            if (it) (activity as KakaoSignUpActivity).nextButtonClicked()
        }
    }

    override fun showCheckImage() {
        nicknameBinding.nicknameDuplicatedCheck.visibility = View.VISIBLE
    }

    override fun hideCheckImage() {
        nicknameBinding.nicknameDuplicatedCheck.visibility = View.GONE
    }

    override fun showErrorImage() {
        nicknameBinding.nicknameDuplicatedCheck.visibility = View.VISIBLE
    }

    override fun hideErrorImage() {
        nicknameBinding.nicknameDuplicatedCheck.visibility = View.GONE
    }

    override fun setValidatedNickname() = nicknameCheck.set(true)
    override fun unsetValidatedNickname() = nicknameCheck.set(false)
}
