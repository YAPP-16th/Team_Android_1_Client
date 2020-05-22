package com.eroom.erooja.feature.goalEdit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.loadDrawable
import com.eroom.domain.utils.toastShort
import com.eroom.domain.utils.vibrateShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityGoalEditBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import org.koin.android.ext.android.get

class GoalEditActivity : AppCompatActivity(), GoalEditContract.View {
    lateinit var binding: ActivityGoalEditBinding
    lateinit var presenter: GoalEditPresenter

    lateinit var baseTitle: String
    lateinit var baseDescription: String
    private var goalId: Long = -1
    private var isMoreThanMaxLength = false

    private val contentDiff: MutableLiveData<Boolean> = MutableLiveData(false)
    val underlineError: ObservableField<Boolean> = ObservableField(false)
    val lengthIsFine: ObservableField<Boolean> = ObservableField(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
        observeData()
    }

    fun initPresenter() {
        presenter = GoalEditPresenter(this, get())
    }

    fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal_edit)
        binding.activity = this
    }

    @SuppressLint("SetTextI18n")
    fun initView() {
        baseTitle = intent.getStringExtra(Consts.GOAL_TITLE) ?: ""
        baseDescription = intent.getStringExtra(Consts.DESCRIPTION) ?: ""
        goalId = intent.getLongExtra(Consts.GOAL_ID, -1)
        binding.goalTitle.setText(baseTitle)
        binding.goalDetailContent.setText(baseDescription)
        binding.goalTitleLength.text = "${binding.goalTitle.text.length}/50"
        binding.goalTitle.addTextChangedListener {
            it?.let { editable -> if (editable.length >= 50) {
                if (!isMoreThanMaxLength) {
                    isMoreThanMaxLength = true
                    Thread(Runnable {
                        this.vibrateShort()
                    }).start()
                }
            } else {
                isMoreThanMaxLength = false
            } }
            binding.goalTitleLength.text = "${it?.length ?: ""}/50"
            it?.length?.let { length: Int ->
                lengthIsFine.set(length >= 5)
            }
            lengthIsFine.get()?.let { boolean -> underlineError.set(!boolean) }
            contentDiff.value = !(it.toString() == baseTitle && binding.goalDetailContent.text.toString() == baseDescription)
        }
        binding.goalDetailContent.addTextChangedListener {
            contentDiff.value = !(binding.goalTitle.text.toString() == baseTitle && it.toString() == baseDescription)
        }
    }

    private fun observeData() {
        contentDiff.observe(this, Observer {
            if (lengthIsFine.get()!!) {
                if (it) {
                    binding.completeImage.loadDrawable(getDrawable(R.drawable.ic_icon_check_active))
                } else {
                    binding.completeImage.loadDrawable(getDrawable(R.drawable.ic_icon_check_inactive))
                }
            } else
                binding.completeImage.loadDrawable(getDrawable(R.drawable.ic_icon_check_inactive))
        })
    }

    fun navigateToFinish() {
        if (lengthIsFine.get()!!) {
            contentDiff.value?.let {
                if (it) {
                    startActivityForResult(Intent(this, EroojaDialogActivity::class.java).apply {
                        putExtra(Consts.DIALOG_CONTENT, "수정된 목표 내용을 저장하시겠어요?")
                        putExtra(Consts.DIALOG_CONFIRM, true)
                        putExtra(Consts.DIALOG_CANCEL, true)
                    }, 5000)
                } else {
                    finish()
                }
            } ?: kotlin.run { finish() }
        } else {
            startActivityForResult(Intent(this, EroojaDialogActivity::class.java).apply {
                putExtra(Consts.DIALOG_CONTENT, "수정중인 내역이 사라질 수 있습니다. 정말 그만두시겠어요?")
                putExtra(Consts.DIALOG_CONFIRM, true)
                putExtra(Consts.DIALOG_CANCEL, true)
            }, 6000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5000 && resultCode == 6000) {
            data?.let {
                val result = it.getBooleanExtra(Consts.DIALOG_RESULT, false)
                if (result) {
                    requestEdit()
                } else {
                    finish()
                }
            }
        }
        if (requestCode == 6000 && resultCode == 6000) {
            data?.let {
                if (it.getBooleanExtra(Consts.DIALOG_RESULT, false)) {
                    finish()
                }
            }
        }
    }

    fun requestEdit() {
        if (lengthIsFine.get()!!) {
            presenter.requestEditGoal(
                goalId,
                binding.goalTitle.text.toString(),
                binding.goalDetailContent.text.toString()
            )
        }
    }

    override fun finishEdit() {
        finish()
    }

    override fun showMessage() {
        this.toastShort("목표수정에 실패하였습니다")
    }

    override fun onBackPressed() {
        navigateToFinish()
    }
}
