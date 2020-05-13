package com.eroom.erooja.feature.setting.setting_nickname

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.NicknameChangeBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.fragment_nickname.*
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit


class NicknameChangeFragment: BottomSheetDialogFragment(), NicknameChangeContract.View {
    lateinit var mBinding: NicknameChangeBottomSheetBinding
    val nickname: MutableLiveData<String> = MutableLiveData()
    var nicknameCheck: ObservableField<Boolean> = ObservableField(true) //true 면 ㄴㄴ, false 면 ㅇㅋ

    lateinit var presenter : NicknameChangePresenter


    companion object {
        fun newInstance() =
            NicknameChangeFragment()
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme_round

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPresenter()
        setUpDataBinding(inflater, container)
        initView()
        return mBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = NicknameChangeBottomSheetBinding.inflate(inflater, container, false)
    }

    private fun initPresenter(){
        presenter = NicknameChangePresenter(this, get())
    }

    private fun initView() {
         RxTextView.textChanges(mBinding.nicknameText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe {
                    nickname.value = it
                    mBinding.nicknameDuplicatedCheck.visibility = View.VISIBLE
                    mBinding.nicknameLengthError.visibility =
                        if (!it.contains(" ")) {
                            if (it.length in 1..1) {
                                unsetValidatedNickname()
                                View.VISIBLE
                            } else View.INVISIBLE
                        } else View.VISIBLE
                    if (!it.contains(" ")) {
                        if (it.length >= 2)
                            presenter.checkNickname(it)
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
        arguments?.let { mBinding.nicknameText.setText(it.getString(Consts.NICKNAME) ?: "") }
    }


    override fun showCheckImage() {
        mBinding.nicknameDuplicatedCheck.visibility = View.VISIBLE
    }

    override fun hideCheckImage() {
        mBinding.nicknameDuplicatedCheck.visibility = View.GONE

    }

    override fun showErrorImage() {
        mBinding.nicknameDuplicatedCheckError.visibility = View.VISIBLE
    }

    override fun hideErrorImage() {
        mBinding.nicknameDuplicatedCheckError.visibility = View.GONE
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