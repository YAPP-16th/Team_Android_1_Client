package com.eroom.erooja.app.feature.addGoal.newGoalPage.selectjob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.databinding.FragmentSelectJobBinding
import com.eroom.erooja.app.feature.addGoal.newGoalFrame.NewGoalActivity
import org.koin.android.ext.android.get


class SelectJobFragment : Fragment(), SelectJobContract.View {
    private lateinit var selectJobBinding: FragmentSelectJobBinding
    private lateinit var presenter: SelectJobPresenter
    val selectList: MutableLiveData<ArrayList<Long>> = MutableLiveData()
    val selectCheck: MutableLiveData<Boolean> = MutableLiveData()
    private val selectedId: ArrayList<Long> = ArrayList()
    private lateinit var mAdapter: SelectJobAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            SelectJobFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPresenter()
        setUpDataBinding(inflater, container)
        initView()
        return selectJobBinding.root
    }

    private fun initPresenter() {
        presenter = SelectJobPresenter(this, get(), get())
        arguments?.let {
            it.getLongArray(Consts.INTERESTED_JOB_CLASS)?.toTypedArray()?.let { array ->
                selectedId.addAll(array)
                selectList.value = selectedId
            }
        }
        selectCheck.value = selectedId.size != 0
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        selectJobBinding = FragmentSelectJobBinding.inflate(inflater, container, false)
        selectJobBinding.fragment = this
    }

    private fun initView() {
        (activity as NewGoalActivity).startAnimation()
        presenter.getJobGroups()
    }

    override fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>) =
        jobGroupList.map {
            it.id
        }.toList().let {
            presenter.getJobGroupAndClasses(it)
        }

    override fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>) {
        context?.let {
            mAdapter = SelectJobAdapter(it, result, selectedId, itemClick)
            selectJobBinding.jobGroupRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private val itemClick = { id: Long, preState: Boolean ->
        if (preState)
            selectedId.remove(id)
        else
            selectedId.add(id)
        checkSelect()
        selectList.value = selectedId
        mAdapter.notifyDataSetChanged()
    }

    private fun checkSelect() {
        selectCheck.value = selectedId.size != 0
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }

    override fun stopAnimation() = (activity as NewGoalActivity).stopAnimation()
}
