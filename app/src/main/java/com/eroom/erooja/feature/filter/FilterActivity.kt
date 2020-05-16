package com.eroom.erooja.feature.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityFilterBinding
import com.eroom.erooja.singleton.JobClassHashMap.hashmap
import org.koin.android.ext.android.get
import timber.log.Timber


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

        val settingTxt: String? = intent.getStringExtra("JOB_CLASS_CHANGE")
        settingTxt?.let{
            filterBinding.filterText.text = settingTxt
            filterBinding.closeButton.setImageResource(R.drawable.ic_icon_navi_arrow_left)
        }

        if(intent.getIntExtra(Consts.SETTING_REQUEST,1) == Consts.SETTING_REQUEST_NUM){
            filterBinding.resetText.text = resources.getText(R.string.save_txt)
        }
    }

    override fun reRequestClassByGroup(jobGroupList: ArrayList<com.eroom.data.entity.JobGroup>) =
        jobGroupList.map {
            it.id //직군 (디자인 or 개발) 불러오기
        }.toList().let {
            presenter.getJobGroupAndClasses(it)
        }

    //직무 직군 버튼 다 가져오는 함수임
    override fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>) {
        val interestNum = intent.getSerializableExtra("search") as ArrayList<Long>
        for(i in interestNum){
            selectedId.add(i)
        }

        mAdapter = JobGroupAdapter(this, result, selectedId, itemClick)
        filterBinding.jobGroupRecycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        checkSelect()
    }

    private val itemClick = { id: Long, preState: Boolean ->
        if (preState)
            selectedId.remove(id)
        else
            selectedId.add(id)
        checkSelect()
        mAdapter.notifyDataSetChanged()
    }

    fun completeButtonClicked() {
        val result1 = intent.putExtra("selectedId",selectedId)
        val result2 = intent.putExtra("HashMap", hashmap)
        setResult(1000, result1)
        setResult(1000, result2)

        if(classCheck.get()!!) finish()

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
