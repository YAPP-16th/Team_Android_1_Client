package com.eroom.erooja.feature.search.search_detail_frame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var mAdapter: SearchResultAdapter
    private var mPage = 0
    private var mContentSize = 0
    private var mContentList: ArrayList<GoalContent> = ArrayList()
    private var isEnd = false

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
        resetOption()
        presenter.getSearchJobInterest(fragmentKey, mPage)
        fragmentTitle = null
    }

    fun setTitle(title: String?) {
        fragmentTitle = title
        resetOption()
        presenter.getSearchGoalTitle(fragmentTitle, mPage)
        fragmentKey = null
    }

    private fun resetOption() {
        mPage = 0
        mContentSize = 0
        mContentList.clear()
        isEnd = false
    }

    override fun setAllView(search: ArrayList<GoalContent>) {
        (activity as SearchDetailActivity).checkContentSize(mPage == 0 && search.size == 0)
        mContentSize += search.size
        mContentList.addAll(search)
        if (mPage == 0) {
            mAdapter = SearchResultAdapter(
                presenter.getGoalContentCallback(),
                mContentList,
                itemClick,
                requireContext()
            )
            binding.searchResultRecyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
            }
        } else {
            mAdapter.submitList(mContentList)
            mAdapter.notifyDataSetChanged()
        }
        mPage++
    }

    private val itemClick = { goalId: Long ->
        startActivity(
            Intent(
                activity,
                GoalDetailActivity::class.java
            ).apply { putExtra(Consts.GOAL_ID, goalId) })
    }

    override fun setIsEnd(boolean: Boolean) {
        isEnd = boolean
    }

    val recyclerViewScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy >= 0 && mContentSize > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == mContentSize - 1 && !isEnd) {
                        fragmentKey?.let { presenter.getSearchJobInterest(it, mPage) }
                        fragmentTitle?.let { presenter.getSearchGoalTitle(it, mPage) }
                    }
                }
            }
        }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}