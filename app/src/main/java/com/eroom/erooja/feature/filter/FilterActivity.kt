package com.eroom.erooja.feature.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.localclass.*
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityFilterBinding
import org.koin.android.ext.android.get

class FilterActivity : AppCompatActivity(), FilterContract.View {
    private lateinit var presenter: FilterPresenter
    private lateinit var filterBinding: ActivityFilterBinding
    private lateinit var mAdapter: JobGroupAdapter

    val classCheck: ObservableField<Boolean> = ObservableField(false)
    private val selectedId: ArrayList<Long> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter = FilterPresenter(this, get(), get())
    }

    private fun setUpDataBinding() {
        filterBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        filterBinding.activity = this
    }

    private fun initView() {
        presenter.getJobGroups()
    }

    override fun reRequestClassByGroup(jobGroupList: ArrayList<com.eroom.data.entity.JobGroup>) =
        jobGroupList.map {
            it.id
        }.toList().let {
            presenter.getJobGroupAndClasses(it)
        }

    override fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>) {
        mAdapter = JobGroupAdapter(this, result, selectedId, itemClick)
        filterBinding.jobGroupRecycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private val itemClick = { id: Long, preState: Boolean ->
        if (preState)
            selectedId.remove(id)
        else
            selectedId.add(id)
        checkSelect()
        mAdapter.notifyDataSetChanged()
    }

    fun resetButtonClicked() {
        selectedId.clear()
        mAdapter.notifyDataSetChanged()
        classCheck.set(false)
    }

    fun closeButtonClicked() {
        finish()
    }

    private fun checkSelect() {
        classCheck.set(selectedId.size != 0)
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
