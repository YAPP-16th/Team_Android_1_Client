package com.eroom.erooja.feature.setting.setting_nickname

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityChangeNicknameBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import org.koin.android.ext.android.get
import java.util.regex.Pattern

class NicknameChangeActivity : AppCompatActivity(), NicknameChangeContract.View{
    lateinit var mBinding: ActivityChangeNicknameBinding
    val nickname: MutableLiveData<String> = MutableLiveData()
    var nicknameCheck: ObservableField<Boolean> = ObservableField(false)
    val showCheckBtn: ObservableField<Boolean> = ObservableField(false)
    lateinit var presenter: NicknameChangePresenter
    private var originalNickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDataBinding()
        initPresenter()
    }

    private fun setupDataBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_nickname)
        mBinding.changeNickname = this
    }

    private fun initPresenter() {
        presenter = NicknameChangePresenter(this, get(), get(), get())
        presenter.getMyNickname()
    }

    override fun setMyNickname(nickname: String) {
        mBinding.nicknameText.setText(nickname)
        originalNickname = nickname
        initView()
    }
    private fun initView() {
        mBinding.nicknameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.toString() != "") {
                        nickname.value = it.toString()
                        if (!originalNickname.equals(it.toString())) {
                            if (!it.contains(" ") && Pattern.matches("^[ㄱ-ㅣ|ㅏ-ㅣ|가-힣\\s]*$", it) && it.length in 2..5) {
                                presenter.checkNickname(it.toString())
                            }
                            else {
                                mBinding.nicknameErrorText.text =
                                    resources.getString(R.string.nickname_rule_info)
                                mBinding.nicknameErrorText.visibility = View.VISIBLE
                                nicknameCheck.set(false)
                            }

                        } else {
                            mBinding.nicknameErrorText.visibility = View.INVISIBLE
                            nicknameCheck.set(false)
                        }
                    }
                        else {
                                mBinding.nicknameErrorText.text =
                                    resources.getString(R.string.nickname_rule_info)
                                mBinding.nicknameErrorText.visibility = View.VISIBLE
                                nicknameCheck.set(false)
                            }
                        }
                    }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
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
                this,
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
                    this,
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
        } else finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1300 && resultCode == 6000) {
            data.let {
                val result = it?.getBooleanExtra(Consts.DIALOG_RESULT, false)
                if (result!!) { //확인
                    presenter.onCleared()
                    finish()
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
}