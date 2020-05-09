package com.eroom.erooja.feature.search.search_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.getKeyFromValue
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.goalDetail.GoalDetailActivity
import com.eroom.erooja.feature.search.search_detail_frame.SearchNoContentFragment
import com.eroom.erooja.feature.search.search_detail_frame.SearchResultAdapter
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.search.search_main_frame.SearchNoGoalListFragment
import com.eroom.erooja.singleton.JobClassHashMap
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.get
import timber.log.Timber
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var searchBinding: FragmentSearchBinding
    private lateinit var presenter: SearchPresenter
    private var selectedGroupClassesNum = ArrayList<Long>()
    private var selectedGroupClassesName = ArrayList<String>()
    private var confirmCheck : ObservableField<Boolean> = ObservableField(true)
    private var mPage: Int = 0
    private var mContentSize = 0
    private var isEnd = false
    private var mKey: Long? = null
    private lateinit var mAdapter: SearchResultAdapter

    private lateinit var emptyFragment: SearchNoContentFragment

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initView()
    }

    override fun onStart() {
        super.onStart()
        initFragment()
        tabSelected()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        return searchBinding.root
    }

    private fun initFragment() {
        emptyFragment = SearchNoContentFragment.newInstance()
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.search = this
    }

    private fun initView() {
        presenter = SearchPresenter(this, get(), get())
        presenter.getAlignedJobInterest()
    }

    private fun tabSelected() {
        searchBinding.searchMainTablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab) {
                mContentSize = 0
                mPage = 0
                isEnd = false
                searchInfoRequest(p0.text.toString())
            }
        })
    }

    private fun searchInfoRequest(text: String) {
        val key = JobClassHashMap.hashmap.getKeyFromValue(text)
        key?.let {
            mKey = it
            presenter.getSearchJobInterest(mKey, mPage)
        }
    }

    private fun setResultFragment() {
        showResult()
        hideEmptyFragment()
    }

    private fun setEmptyFragment() {
        emptyFragment()
        hideResult()
    }

    private fun showResult() {
        searchBinding.mainResultRecycler.visibility = View.VISIBLE
    }

    private fun hideResult() {
        searchBinding.mainResultRecycler.visibility = View.GONE
    }

    private fun emptyFragment() =
        childFragmentManager.beginTransaction()
            .add(R.id.search_main_container, emptyFragment)
            .commit()

    private fun hideEmptyFragment() =
        childFragmentManager.beginTransaction()
            .remove(emptyFragment)
            .commit()

    override fun setAlignedJobInterest(interest: MutableSet<String>) {
        var isFirst = true
        when(searchBinding.searchMainTablayout.tabCount) {
            0 -> {
                interest.forEach {
                    if (isFirst) {
                        searchInfoRequest(it)
                        isFirst = false
                    }
                    searchBinding.searchMainTablayout.addTab(
                        searchBinding.searchMainTablayout.newTab().setText(it)
                    )
                }
            }
            else -> {
                Timber.i("PASS")
            }
        }
    }

    override fun setAllView(search: ArrayList<GoalContent>) {
        mContentSize += search.size
        if (mPage == 0) {
            if (mContentSize == 0) {
                setEmptyFragment()
            } else {
                mAdapter =
                    SearchResultAdapter(presenter.getGoalContentCallback(), search, itemClick)
                searchBinding.mainResultRecycler.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = mAdapter
                }
            }
        } else {
            mAdapter.submitList(search)
            mAdapter.notifyDataSetChanged()
        }
        mPage++
    }

    private val itemClick = { goalId: Long ->
        startActivity(Intent(activity, GoalDetailActivity::class.java).apply { putExtra(Consts.GOAL_ID, goalId) })
    }

    override fun setIsEnd(boolean: Boolean) {
        isEnd = boolean
        if (boolean && mPage == 0) setEmptyFragment()
        else setResultFragment()
    }

    val recyclerViewScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy >= 0 && mContentSize > 0) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == mContentSize - 1 && !isEnd) {
                    presenter.getSearchJobInterest(mKey, mPage)
                }
            }
        }
    }

    override fun setUserJobInterest(interest: MutableSet<JobClass>) {
        interest.map{
            selectedGroupClassesNum.add(it.id)
        }
    }

    fun searchClick(v: View){
        activity?.let {
            val intent = Intent(context, SearchDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000 && resultCode == 1000) {
            selectedGroupClassesName.clear()
            selectedGroupClassesNum.clear()

            val result1 = data?.getSerializableExtra("selectedId") as ArrayList<Long>
            val result2 = data?.getSerializableExtra("HashMap") as HashMap<Long, String>

            repeat(result1.size) {
                result2[result1[it]]?.let { it ->
                    selectedGroupClassesName.add(it) }

                selectedGroupClassesNum.add(result1[it])
            }

            confirmCheck?.let{
                updateTab(selectedGroupClassesName) }
                .also{ confirmCheck.set(false) }
        }
    }

    private fun updateTab(it: ArrayList<String>){
        searchBinding.searchMainTablayout.removeAllTabs()

        it.forEach {
            searchBinding.searchMainTablayout.addTab(
                searchBinding.searchMainTablayout.newTab().setText(it)
            )
        }
    }

    fun openSearchFilter() {
        val intent = Intent(activity, FilterActivity::class.java)
        var number = ArrayList<Long>()

        repeat(selectedGroupClassesNum.size){
            number.add(selectedGroupClassesNum[it])
        }

        intent.putExtra("search",number)
        startActivityForResult(intent, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        childFragmentManager.beginTransaction().detach(emptyFragment)
    }
}