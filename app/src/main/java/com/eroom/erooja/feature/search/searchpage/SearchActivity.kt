package com.eroom.erooja.feature.search.searchpage


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySearchBinding
import com.eroom.erooja.feature.search.searchframe.SearchJobClassFragment
import com.eroom.erooja.feature.search.searchframe.SearchJobGoalFragment
import com.eroom.erooja.feature.search.searchframe.SearchNoContentFragment
import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class SearchActivity : AppCompatActivity(), SearchContract.View {
    var changeNum : Int = 0
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
                SearchJobClassFragment.newInstance(),
                SearchJobGoalFragment.newInstance(),
                SearchNoContentFragment.newInstance()
            ))
        }.map {
            it.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.search_container, it).commit()
            }
        }.run {
            loadFragment(0)
        }


    private fun loadFragment(index: Int) =
        searchframe.map {
            it.apply { supportFragmentManager.beginTransaction().hide(this).commit() }
        }[index].also {
            supportFragmentManager.beginTransaction().show(it).commit()
        }

    private fun setUpDataBinding(){
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchBinding.search = this@SearchActivity
    }

    private fun initView() {
        searchBinding.searchTablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab) {
                var pos = p0.position
                changeView(pos)
            }
        })


        RxTextView.textChanges(searchBinding.searchEditText)
            .debounce (300, TimeUnit.MILLISECONDS)
            .map{it.toString()}
            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                //Todo
                searchword.value = it
                this@SearchActivity.toastShort("${it.length}")

                searchword.value.run{
                   changeNum = if(it.isEmpty()) search_tablayout.selectedTabPosition
                                else 2 //Temp!
                }
                loadFragment(changeNum)

            }
    }


    override fun changeView(pos : Int){
        when(pos){
            0 -> loadFragment(0)
            1 -> loadFragment(1)
        }

    }
}


