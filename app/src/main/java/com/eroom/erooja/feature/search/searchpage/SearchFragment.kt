package com.eroom.erooja.feature.search.searchpage


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivitySearchBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_search.*


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : FragmentActivity(), SearchContract.View {
    val tabLayoutTextArray = arrayOf("직무","직군")
    private lateinit var searchBinding: ActivitySearchBinding
    //private lateinit var presenter: SearchPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search)
        setUpDataBinding()
        initView()
    }

//    companion object {
//        @JvmStatic
//        fun newInstance() = SearchFragment()
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        presenter = SearchPresenter(this)
//    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setUpDataBinding(inflater, container)
//        initView()
//        return searchBinding.root
//    }

//    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
//        searchBinding = ActivitySearchBinding.inflate(inflater, container, false)
//        searchBinding.fragment = this
//    }

    private fun setUpDataBinding(){
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchBinding.search = this@SearchFragment
    }

    private fun initView() {

        searchBinding.viewpager2.apply {
            adapter = SearchFrameAdapter(this@SearchFragment)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        TabLayoutMediator(tablayout,viewpager2){tab,position->
            tab.text = tabLayoutTextArray[position]
        }.attach()
    }
}

