package com.eroom.erooja.feature.search.search_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.data.entity.JobClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.search.search_main_frame.SearchNoGoalListFragment
import org.koin.android.ext.android.get
import timber.log.Timber


class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var searchBinding: FragmentSearchBinding
    private lateinit var presenter: SearchPresenter
    private lateinit var selectedGroupClassesNum: ArrayList<Long>



    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       presenter = SearchPresenter(this, get())
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
        presenter.getAlignedJobInterest()
    }

    override fun setAlignedJobInterest(interest: MutableSet<String>) {
        interest.forEach {
            searchBinding.searchMainTablayout.addTab(
                searchBinding.searchMainTablayout.newTab().setText(it)
            )
        }
    }

    override fun setUserJobInterest(interest: ArrayList<JobClass>) {
        selectedGroupClassesNum = ArrayList()
        interest.map{
            selectedGroupClassesNum.add(it.id)
        }
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
        val intent = Intent(activity, FilterActivity::class.java)

        repeat(selectedGroupClassesNum.size){
            intent.putExtra("search_${selectedGroupClassesNum[it]}",selectedGroupClassesNum[it])
            Timber.i("search test! ")
        }
        startActivity(intent)

        //startActivity(Intent(activity, FilterActivity::class.java))
    }
}