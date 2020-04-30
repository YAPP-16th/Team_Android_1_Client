package com.eroom.erooja.feature.search.search_detail_frame

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.GoalContent
import com.eroom.domain.utils.toastLong
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchResultListBinding
import org.koin.android.ext.android.get
import java.util.*
import kotlin.collections.ArrayList

class SearchResultFragment : Fragment(), SearchResultContract.View {
    private lateinit var binding: FragmentSearchResultListBinding
    private lateinit var presenter: SearchResultPresenter
    private var fragmentKey: Long ?=null
    private var fragmentTitle: String ?=null
    private var flag : ObservableField<Boolean> = ObservableField(false)


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

    override fun updateView(search: ArrayList<GoalContent>) {
        binding.searchResultRecyclerview.apply{
            removeAllViewsInLayout()
            adapter = SearchResultAdapter(search,Click)
        }
        flag.set(false)
    }

    private fun updateAdater(){
        binding.searchResultRecyclerview.adapter?.apply {
            notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getSearchJobInterest(fragmentKey)
        updateAdater()
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSearchResultListBinding.inflate(inflater, container, false)
        binding.searchResult = this
    }

    fun setTitle(title : String?){
        fragmentTitle = title
        presenter.getSearchGoalTitle(fragmentTitle)
        updateAdater()
    }

    fun setKey(){
        fragmentKey = arguments?.let{
            it.getLong("key")
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = SearchResultPresenter(this, get())

    }

    override fun setAllView(search: ArrayList<GoalContent>) {
        binding.searchResultRecyclerview.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = SearchResultAdapter(search, Click)
        }

        binding.searchResultRecyclerview.adapter?.apply {
            notifyDataSetChanged()
        }
    }


    private val Click = { index:Int ->

    }

}