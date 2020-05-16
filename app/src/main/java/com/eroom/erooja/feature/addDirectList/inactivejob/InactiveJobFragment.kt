package com.eroom.erooja.feature.addDirectList.inactivejob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.erooja.databinding.FragmentInactiveJobBinding
import org.koin.android.ext.android.get


class InactiveJobFragment : Fragment(), InactiveJobContract.View {
    private lateinit var selectJobBinding: FragmentInactiveJobBinding
    private lateinit var presenter: InactiveJobPresenter
    private val selectedId: ArrayList<Long> = ArrayList()
    private lateinit var mAdapter: InactiveJobAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            InactiveJobFragment()
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
        presenter = InactiveJobPresenter(this, get(), get())
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        selectJobBinding = FragmentInactiveJobBinding.inflate(inflater, container, false)
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
            mAdapter = InactiveJobAdapter(it, result, selectedId)
            selectJobBinding.jobGroupRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
