package com.eroom.erooja.feature.addDirectList.enabledjob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.erooja.databinding.FragmentEnabledJobBinding
import com.eroom.erooja.feature.addGoal.newGoalPage.selectjob.SelectJobAdapter
import com.eroom.erooja.feature.addGoal.newGoalPage.selectjob.SelectJobContract
import com.eroom.erooja.feature.addGoal.newGoalPage.selectjob.SelectJobPresenter
import org.koin.android.ext.android.get


class EnabledJobFragment : Fragment(), EnabledJobContract.View {
    private lateinit var selectJobBinding: FragmentEnabledJobBinding
    private lateinit var presenter: EnabledJobPresenter
    private val selectedId: ArrayList<Long> = ArrayList()
    private lateinit var mAdapter: EnabledJobAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            EnabledJobFragment()
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
        presenter = EnabledJobPresenter(this, get(), get())
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        selectJobBinding = FragmentEnabledJobBinding.inflate(inflater, container, false)
        selectJobBinding.fragment = this
    }

    private fun initView() {
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
            mAdapter = EnabledJobAdapter(it, result, selectedId)
            selectJobBinding.jobGroupRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
            } }
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
