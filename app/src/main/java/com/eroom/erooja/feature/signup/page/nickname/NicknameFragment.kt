package com.eroom.erooja.feature.signup.page.nickname


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.globalconst.Consts

import com.eroom.erooja.databinding.FragmentNicknameBinding
import com.eroom.erooja.feature.signup.kakao.KakaoSignUpActivity
import kotlinx.android.synthetic.main.fragment_nickname.*
import org.koin.android.ext.android.get

/**
 * A simple [Fragment] subclass.
 */
class NicknameFragment : Fragment(), NicknameContract.View {
    private lateinit var nicknameBinding: FragmentNicknameBinding
    private lateinit var presenter: NicknamePresenter

    val nickname: MutableLiveData<String> = MutableLiveData()
    var nicknameCheck: ObservableField<Boolean> = ObservableField(true)

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
        nicknameBinding.nicknameText.addTextChangedListener(object :TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { if (it.toString() != "") {
                    nickname.value = s.toString()
                    nicknameBinding.nicknameLengthError.visibility =
                        if (!s.toString().contains(" ")) {
                            if (s.toString().length in 1..1) {
                                unsetValidatedNickname()
                                View.VISIBLE
                            } else View.INVISIBLE
                        } else View.VISIBLE
                    if (!s.toString().contains(" ")) {
                        if (s.toString().length >= 2)
                            presenter.checkNickname(s.toString())
                        else {
                            unsetDuplicatedNickname()
                            unsetValidatedNickname()
                            hideCheckImage()
                            hideErrorImage()
                        }
                    } else {
                        unsetDuplicatedNickname()
                        unsetValidatedNickname()
                        showErrorImage()
                        hideCheckImage()
                    }
                }
            } }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        arguments?.let { nicknameBinding.nicknameText.setText(it.getString(Consts.NICKNAME) ?: "") }
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
        nicknameBinding.nicknameDuplicatedCheckError.visibility = View.VISIBLE
    }

    override fun hideErrorImage() {
        nicknameBinding.nicknameDuplicatedCheckError.visibility = View.GONE
    }

    override fun setDuplicatedNickname() {
        nicknameDuplicatedTextError.visibility = View.VISIBLE
    }

    override fun unsetDuplicatedNickname() {
        nicknameDuplicatedTextError.visibility = View.INVISIBLE
    }

    override fun setValidatedNickname() = nicknameCheck.set(true)
    override fun unsetValidatedNickname() = nicknameCheck.set(false)

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
