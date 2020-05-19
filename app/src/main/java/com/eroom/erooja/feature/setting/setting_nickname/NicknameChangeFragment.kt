package com.eroom.erooja.feature.setting.setting_nickname

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.NicknameChangeBottomSheetBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.feature.setting.SettingFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding.widget.RxTextView
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit


class NicknameChangeFragment : BottomSheetDialogFragment(), NicknameChangeContract.View {
    lateinit var mBinding: NicknameChangeBottomSheetBinding
    val nickname: MutableLiveData<String> = MutableLiveData()
    var nicknameCheck: ObservableField<Boolean> = ObservableField(false)
    val showCheckBtn: ObservableField<Boolean> = ObservableField(false)
    lateinit var presenter: NicknameChangePresenter
    private var originalNickname = ""

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
        mBinding.fragment = this@NicknameChangeFragment
    }

    private fun initPresenter() {
        presenter = NicknameChangePresenter(this, get(), get(), get())
        presenter.getMyNickname()
    }

    override fun setMyNickname(nickname: String) {
        mBinding.nicknameText.setText(nickname)
        originalNickname = nickname
    }

    private fun initView() {
        RxTextView.textChanges(mBinding.nicknameText)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                nickname.value = it
                if (originalNickname.equals(it)) //pass
                else {
                    if (!it.contains(" ")) {
                        if (it.length in 2..5)
                            presenter.checkNickname(it)
                        else {
                            mBinding.nicknameErrorText.text =
                                resources.getString(R.string.nickname_rule_info)
                            mBinding.nicknameErrorText.visibility = View.VISIBLE
                            nicknameCheck.set(false)
                        }
                    } else {
                        mBinding.nicknameErrorText.text =
                            resources.getString(R.string.nickname_rule_info)
                        mBinding.nicknameErrorText.visibility = View.VISIBLE
                        nicknameCheck.set(false)
                    }
                }
            }
    }

    override fun nicknameDuplicationError() {
        mBinding.nicknameErrorText.text = resources.getString(R.string.already_using_nickname)
        mBinding.nicknameErrorText.visibility = View.VISIBLE
        nicknameCheck.set(false)

    }

    override fun nicknameDuplicationPass() {
        mBinding.nicknameErrorText.visibility = View.INVISIBLE
        nicknameCheck.set(true)
        showCheckBtn.set(true)

    }

    fun resetNickname() {
        mBinding.nicknameText.setText("")
        showCheckBtn.set(false)
    }


    fun saveNickname() {
        showAlertBeforeSave()
    }

    fun closeNicknamePage() {
        showAlertBeforeClose()
    }

    private fun showAlertBeforeSave(){
        startActivityForResult(
            Intent(
                context,
                EroojaDialogActivity::class.java
            ).apply {
                putExtra(Consts.DIALOG_TITLE, "")
                putExtra(
                    Consts.DIALOG_CONTENT,
                    "닉네임 변경 내역을 저장하시겠어요?"
                )
                putExtra(Consts.DIALOG_CONFIRM, true)
                putExtra(Consts.DIALOG_CANCEL, true)
            }, 1400
        )
    }

    private fun showAlertBeforeClose(){
        //닉네임이 변경되었다면
        if(!originalNickname.equals(nickname.value)){
            startActivityForResult(
                Intent(
                    context,
                    EroojaDialogActivity::class.java
                ).apply {
                    putExtra(Consts.DIALOG_TITLE, "")
                    putExtra(
                        Consts.DIALOG_CONTENT,
                        "닉네임 변경이 완료되지 않았습니다.\n변경을 취소하시겠어요?"
                    )
                    putExtra(Consts.DIALOG_CONFIRM, true)
                    putExtra(Consts.DIALOG_CANCEL, true)
                }, 1300
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1300 && resultCode == 6000) {
            data.let {
                val result = it?.getBooleanExtra(Consts.DIALOG_RESULT, false)
                if (result!!) { //확인
                    (parentFragment as SettingFragment).dismissBottomSheet()
                    presenter.onCleared()
                }
            }
        } else if ( requestCode == 1400 && resultCode == 6000) {
            presenter.updateNickname(mBinding.nicknameText.text.toString())
            originalNickname = mBinding.nicknameText.text.toString()
            showCheckBtn.set(false)
            nicknameCheck.set(false)
            initView()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        showAlertBeforeClose()
      //  super.onDestroy()
    }
}

