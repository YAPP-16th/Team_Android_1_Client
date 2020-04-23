package com.eroom.erooja.feature.search.search_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.data.localclass.DesignClass
import com.eroom.data.localclass.DevelopClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.search.search_main_frame.SearchNoGoalListFragment
import com.google.android.material.tabs.TabLayout

class SearchFragment : Fragment(), SearchContract.View{
    //var searchFrame: ArrayList<Fragment> = ArrayList()
    private lateinit var searchBinding: FragmentSearchBinding
    //private lateinit var presenter: SearchFragment


    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       // presenter = SearchPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return searchBinding.root
    }

    override fun onStart() {
        super.onStart()
        initChildFragment()
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.search = this
    }

    private fun initView() {
        for (i in 0 until 9) {
            searchBinding.searchMainTablayout.addTab(
                searchBinding.searchMainTablayout.newTab().setText(
                    DesignClass.getArray()[i].getName())
            )
            searchBinding.searchMainTablayout.addTab(
            searchBinding.searchMainTablayout.newTab().setText(
                DevelopClass.getArray()[i].getName())
            )

        }

        searchBinding.searchMainTablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab) {
                var pos = p0.position
                changeView(pos)
            }
        })
    }

    fun searchClick(v: View){
        activity?.let {
            var intent = Intent(context, SearchDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initChildFragment() {
                childFragmentManager.beginTransaction()
                    .add(R.id.search_main_container, SearchNoGoalListFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
           // loadChildFragment(0)
        }


    //private fun loadChildFragment(index: Int)()

    private fun changeView(pos: Int){
    }

    fun openSearchFilter() {
        startActivity(Intent(activity, FilterActivity::class.java))
    }
}