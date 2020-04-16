package com.eroom.erooja.feature.search.search_detail_page


import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.eroom.data.localclass.DesignClass
import com.eroom.data.localclass.DevelopClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySearchDetailBinding
import com.eroom.erooja.feature.search.search_detail_frame.SearchJobClassFragment
import com.eroom.erooja.feature.search.search_detail_frame.SearchJobGoalFragment
import com.eroom.erooja.feature.search.search_detail_frame.SearchNoContentFragment
import com.google.android.material.tabs.TabLayout
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_search_detail.*
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 */
class SearchDetailActivity : AppCompatActivity(), SearchDetailContract.View {
    var changeNum : Int = 0
    var searchDeatilFrame: ArrayList<Fragment> = ArrayList()
    val searchword: MutableLiveData<String> = MutableLiveData()
    lateinit var searchAdapter: ArrayAdapter<String>
    var autosearch = ArrayList<String>()

    private lateinit var searchDetailBinding: ActivitySearchDetailBinding
    //private lateinit var presenter: SearchDetailPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
        initFragment()
    }

    private fun initFragment() =
        searchDeatilFrame.apply {
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
        searchDeatilFrame.map {
            it.apply { supportFragmentManager.beginTransaction().hide(this).commit() }
        }[index].also {
            supportFragmentManager.beginTransaction().show(it).commit()
        }

    private fun setUpDataBinding(){
        searchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_detail)
        searchDetailBinding.searchdetail = this@SearchDetailActivity
    }

    private fun initView() {
        searchDetailBinding.searchTablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab) {
                var pos = p0.position
                changeView(pos)
            }
        })

        for (i in 0 until 9){
            autosearch.add(DesignClass.getArray()[i].getName())
            autosearch.add(DevelopClass.getArray()[i].getName())
        }

        searchAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,autosearch)
        searchDetailBinding.searchEditText.setAdapter(searchAdapter)

        RxTextView.textChanges(searchDetailBinding.searchEditText)
            .debounce (300, TimeUnit.MILLISECONDS)
            .map{it.toString()}
            .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                //Todo
                searchword.value = it
                //this@SearchDetailActivity.toastShort("${it.length}")

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

    fun back(view : View){
        finish()
    }
}


