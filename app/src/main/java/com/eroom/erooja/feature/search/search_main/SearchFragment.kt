package com.eroom.erooja.feature.search.search_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.eroom.data.entity.JobClass
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.search.search_main_frame.SearchNoGoalListFragment
import com.kakao.usermgmt.StringSet.name
import org.koin.android.ext.android.get
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var searchBinding: FragmentSearchBinding
    private lateinit var presenter: SearchPresenter
    private var selectedGroupClassesNum = ArrayList<Long>()
    private var selectedGroupClassesName = ArrayList<String>()
    private var comfirmCheck : ObservableField<Boolean> = ObservableField(true)

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
        when(searchBinding.searchMainTablayout.tabCount) {
            0 -> {
                interest.forEach {
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

    override fun setUserJobInterest(interest: MutableSet<JobClass>) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode ==1000 && resultCode==1000 ) {
            selectedGroupClassesName.clear()
            selectedGroupClassesNum.clear()

            val result1 = data?.getSerializableExtra("selectedId") as ArrayList<Long>
            val result2 = data?.getSerializableExtra("HashMap") as HashMap<Long, String>

            repeat(result1.size) {
                result2.get(result1[it])?.let { it ->
                    selectedGroupClassesName.add(it) }

                selectedGroupClassesNum.add(result1[it])
            }


            comfirmCheck.let{
                updateTab(selectedGroupClassesName) }
                .also{ comfirmCheck.set(false) }
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
}