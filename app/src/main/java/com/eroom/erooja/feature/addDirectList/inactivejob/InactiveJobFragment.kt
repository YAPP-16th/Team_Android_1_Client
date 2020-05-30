package com.eroom.erooja.feature.addDirectList.inactivejob

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.databinding.FragmentInactiveJobBinding
import com.eroom.erooja.feature.addDirectList.addMyTodoListPage.AddMyListActivity
import com.eroom.erooja.feature.joinOtherList.joinTodoListPage.JoinOtherListActivity
import org.koin.android.ext.android.get


class InactiveJobFragment : Fragment(), InactiveJobContract.View {
    private lateinit var selectJobBinding: FragmentInactiveJobBinding
    private lateinit var presenter: InactiveJobPresenter
    private val selectedId: ArrayList<Long> = ArrayList()
    private lateinit var mAdapter: InactiveJobClassAdapter
    private var startAnimationPosition = ""

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
        presenter = InactiveJobPresenter(this, get(), get(), get())
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        selectJobBinding = FragmentInactiveJobBinding.inflate(inflater, container, false)
        selectJobBinding.fragment = this
    }

    private fun initView() {
        arguments?.getString(Consts.ADD_MY_LIST)?.let {
            startAnimationPosition = Consts.ADD_MY_LIST
        } ?: run {
            startAnimationPosition = Consts.JOIN_OTHER_LIST
        }

        if (startAnimationPosition.equals(Consts.ADD_MY_LIST)) (activity as AddMyListActivity).startAnimation()
        else (activity as JoinOtherListActivity).startAnimation()

        presenter.getJobGroups()
        presenter.getJobInterestOfTodoList(arguments?.getLong(Consts.GOAL_ID))
    }

    override fun reRequestClassByGroup(jobGroupList: ArrayList<JobGroup>) =
        jobGroupList.map {
            it.id
        }.toList().let {
            presenter.getJobGroupAndClasses(it)
        }

    override fun setJobInterestOfTodoList(jobInterestList: List<Long>) {
        repeat(jobInterestList.size) {
            selectedId.add(jobInterestList[it])
        }
    }

    override fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>) {
        context?.let {
            mAdapter = InactiveJobClassAdapter(it, result, selectedId)
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

    override fun stopAnimation() {
        if (startAnimationPosition.equals(Consts.ADD_MY_LIST)) (activity as AddMyListActivity).stopAnimation()
        else (activity as JoinOtherListActivity).stopAnimation()
    }
}
