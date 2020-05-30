package com.eroom.erooja.app.dialog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityEroojaDialogBinding

class EroojaDialogActivity : AppCompatActivity() {
    private lateinit var newGoalBinding: ActivityEroojaDialogBinding

    companion object {
        const val resultCode = 6000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        newGoalBinding = DataBindingUtil.setContentView(this, R.layout.activity_erooja_dialog)
    }

    private fun initView() {
        val intent = intent
        newGoalBinding.titleText.text = intent.getStringExtra(Consts.DIALOG_TITLE) ?: ""
        if (newGoalBinding.titleText.text == "") {
            newGoalBinding.titleText.visibility = View.GONE
            newGoalBinding.place.visibility = View.VISIBLE
        }
        newGoalBinding.contentText.text = intent.getStringExtra(Consts.DIALOG_CONTENT) ?: ""
        newGoalBinding.confirmText.apply {
            setOnClickListener {
                setResult(resultCode, Intent().apply { putExtra(Consts.DIALOG_RESULT, true) })
                finish()
            }
            visibility = if (intent.getBooleanExtra(Consts.DIALOG_CONFIRM, true)) View.VISIBLE else View.GONE
        }
        newGoalBinding.cancelText.apply {
            setOnClickListener {
                setResult(resultCode, Intent().apply { putExtra(Consts.DIALOG_RESULT, false) })
                finish()
            }
            visibility = if (intent.getBooleanExtra(Consts.DIALOG_CANCEL, true)) View.VISIBLE else View.GONE
        }
    }
}
