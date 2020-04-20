package com.eroom.erooja.feature.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.localclass.*
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity(), FilterContract.View {
    lateinit var presenter: FilterPresenter
    lateinit var filterBinding: ActivityFilterBinding
    lateinit var mAdapter: JobGroupAdapter

    val classCheck: ObservableField<Boolean> = ObservableField(false)

    private lateinit var mDevelopClassInfo: DevelopSelected
    private lateinit var mDesignClassInfo: DesignSelected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        setUpDataBinding()
        initInfo()
        initView()
    }

    private fun initPresenter() {
        presenter = FilterPresenter(this)
    }

    private fun setUpDataBinding() {
        filterBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        filterBinding.activity = this
    }

    private fun initInfo() {
        mDevelopClassInfo = DevelopSelected(DevelopClass.getArray(), ArrayList<Boolean>().apply { repeat(DevelopClass.getArray().size){add(false)} })
        mDesignClassInfo = DesignSelected(DesignClass.getArray(), ArrayList<Boolean>().apply { repeat(DesignClass.getArray().size){add(false)} })
    }

    private fun initView() {
        val group = JobGroup.getGroup()
        mAdapter = JobGroupAdapter(
            group,
            mDevelopClassInfo,
            mDesignClassInfo,
            itemDevClicked,
            itemDesignClicked
        )
        filterBinding.jobGroupRecycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@FilterActivity)
        }
    }

    private val itemDevClicked = { position: Int ->
        mDevelopClassInfo.let { it.isSelected[position] = !it.isSelected[position] }
        mAdapter.notifyDataSetChanged()
        checkInfo()
    }

    private val itemDesignClicked = { position: Int ->
        mDesignClassInfo.let { it.isSelected[position] = !it.isSelected[position] }
        mAdapter.notifyDataSetChanged()
        checkInfo()
    }

    private fun checkInfo() {
        var result = false
        mDevelopClassInfo.let {
            it.isSelected.forEach { boolean ->
                result = result || boolean
            }
        }
        mDesignClassInfo.let {
            it.isSelected.forEach { boolean ->
                result = result || boolean
            }
        }
        classCheck.set(result)
    }

    fun resetButtonClicked() {
        repeat(DevelopClass.getArray().size) {
            mDevelopClassInfo.isSelected[it] = false
        }
        repeat(DesignClass.getArray().size) {
            mDesignClassInfo.isSelected[it] = false
        }
        mAdapter.notifyDataSetChanged()
        classCheck.set(false)
    }

    fun closeButtonClicked() {
        if (classCheck.get()!!) {
            var index = 0
            var result = ""
            mDevelopClassInfo.isSelected.forEach {
                if (it) result += mDevelopClassInfo.classEnum[index].getName() + "\n"
                index++
            }
            index = 0
            mDesignClassInfo.isSelected.forEach {
                if (it) result += mDesignClassInfo.classEnum[index].getName() + "\n"
                index++
            }
            this.toastShort(result)
        }
    }
}
