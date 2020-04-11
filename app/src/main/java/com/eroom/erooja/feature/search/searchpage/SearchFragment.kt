package com.eroom.erooja.feature.search.searchpage


import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySearchBinding
import com.eroom.erooja.feature.main.MainFragment
import com.eroom.erooja.feature.mypage.MyPageFragment
import com.eroom.erooja.feature.search.searchframe.SearchJobClassFragment
import com.eroom.erooja.feature.search.searchframe.SearchJobGoalFragment
import com.eroom.erooja.feature.search.searchframe.SearchNoContentFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.view.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : FragmentActivity(), SearchContract.View {

    var searchframe: ArrayList<Fragment> = ArrayList()
    val searchword: MutableLiveData<String> = MutableLiveData()

    private lateinit var searchBinding: ActivitySearchBinding
    //private lateinit var presenter: SearchPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
        initFragment()
    }

    private fun initFragment() =
        searchframe.apply {
            addAll(listOf(
                SearchJobGoalFragment.newInstance(),
                SearchJobClassFragment.newInstance(),
                SearchNoContentFragment.newInstance()
            ))
        }
    

    private fun setUpDataBinding(){
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchBinding.search = this@SearchFragment
    }

    private fun initView() {
//        searchBinding.searchTablayout.setSelectedTabIndicator()

        RxTextView.textChanges(searchBinding.searchEditText)
            .debounce (300, TimeUnit.MILLISECONDS)
            .map{it.toString()}
            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                //Todo
                searchword.value = it
               // searchBinding.viewpager2[0].run {
                   // searchframe[2]
               // }
            }
    }

}


