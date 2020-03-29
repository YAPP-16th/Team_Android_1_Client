package com.eroom.erooja.feature.exam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityExamBinding
import com.eroom.erooja.feature.exam.fragment.ExamFragment
import org.koin.android.ext.android.get
import timber.log.Timber

class ExamActivity : AppCompatActivity(),
    ExamContract.View {
    private lateinit var examBinding: ActivityExamBinding
    private lateinit var presenter: ExamPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter =
            ExamPresenter(this, get(), get())
    }

    private fun setUpDataBinding() {
        examBinding = DataBindingUtil.setContentView(this, R.layout.activity_exam)
        examBinding.activity = this
    }

    private fun initView() {
        presenter.testGet()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_container,
                ExamFragment.newInstance()
                    .apply { arguments = Bundle().apply { putString("key", "value") } }
            ).commit()
    }

    fun testClick() {
        this.toastShort(listOf("1", "2", "3").join())
        Timber.d("a" add "b")
    }
}
