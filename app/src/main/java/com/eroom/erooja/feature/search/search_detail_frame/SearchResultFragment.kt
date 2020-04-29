package com.eroom.erooja.feature.search.search_detail_frame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchResultListBinding
import org.koin.android.ext.android.get

class SearchResultFragment : Fragment(), SearchResultContract.View {
    private lateinit var binding: FragmentSearchResultListBinding
    private lateinit var presenter: SearchResultPresenter
    private var fragmentKey: Long ?=null
    private var fragmentTitle: String ?=null


    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView()
        setUpDataBinding(inflater, container)
        return binding.root
    }

    private fun initView(){

    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSearchResultListBinding.inflate(inflater, container, false)
        binding.searchResult = this
    }

    fun setKey(activityKey: Long?){
        fragmentKey = activityKey
        //context?.toastLong("resultFragment : ${fragmentKey}")
        presenter.getSearchJobInterest(fragmentKey)
        binding.searchResultRecyclerview.adapter?.apply{
            notifyDataSetChanged()
        }
    }

    fun setTitle(title : String?){
        fragmentTitle = title
        presenter.getSearchGoalTitle(fragmentTitle)
        binding.searchResultRecyclerview.adapter?.apply{
            notifyDataSetChanged()
        }
    }

    //searchresultframgent 실행 순서가 on attach -> setallview -> setkey 임. 그래서 항상 Null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = SearchResultPresenter(this, get())

    }

    override fun setAllView(search: ArrayList<GoalContent>) {
        binding.searchResultRecyclerview.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = SearchResultAdapter(search, Click)
        }
    }


    private val Click = { index:Int ->

    }

}