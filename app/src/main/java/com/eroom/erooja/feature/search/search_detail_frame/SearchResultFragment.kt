package com.eroom.erooja.feature.search.search_detail_frame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalContent
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.databinding.FragmentSearchResultListBinding
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import org.koin.android.ext.android.get
import kotlin.collections.ArrayList

class SearchResultFragment : Fragment(), SearchResultContract.View {
    private lateinit var binding: FragmentSearchResultListBinding
    private lateinit var presenter: SearchResultPresenter
    private var fragmentKey: Long? = null
    private var fragmentTitle: String? = null
    private var mPage = 0

    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = SearchResultPresenter(this, get())
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSearchResultListBinding.inflate(inflater, container, false)
        binding.searchResult = this
    }

    fun setKey(key: Long) {
        fragmentKey = key
        presenter.getSearchJobInterest(fragmentKey)
        fragmentTitle = null
    }

    fun setTitle(title : String?){
        fragmentTitle = title
        presenter.getSearchGoalTitle(fragmentTitle)
        fragmentKey = null
    }

    override fun setAllView(search: ArrayList<GoalContent>) {
        (activity as SearchDetailActivity).checkContentSize(mPage == 0 && search.size == 0)
        if (mPage == 0) {
            binding.searchResultRecyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = SearchResultAdapter(search, itemClick)
            }
        }
        mPage++
    }

    private val itemClick = { goalId: Long ->
        startActivity(Intent(activity, GoalDetailActivity::class.java).apply { putExtra(Consts.GOAL_ID, goalId) })
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}