package com.eroom.erooja.feature.search.search_main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.size
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import com.eroom.data.entity.GoalContent
import com.eroom.data.entity.JobClass
import com.eroom.domain.utils.getKeyFromValue
import com.eroom.domain.utils.toastLong
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentSearchBinding
import com.eroom.erooja.feature.filter.FilterActivity
import com.eroom.erooja.feature.search.search_detail_frame.SearchJobClassFragment
import com.eroom.erooja.feature.search.search_detail_frame.SearchJobGoalFragment
import com.eroom.erooja.feature.search.search_detail_frame.SearchNoContentFragment
import com.eroom.erooja.feature.search.search_detail_frame.SearchResultFragment
import com.eroom.erooja.feature.search.search_detail_page.SearchDetailActivity
import com.eroom.erooja.feature.search.search_main_frame.SearchNoGoalListFragment
import com.eroom.erooja.singleton.JobClassHashMap
import com.google.android.material.tabs.TabLayout
import com.kakao.usermgmt.StringSet.name
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_search_result_list.*
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

    private lateinit var searchFrame: ArrayList<Fragment>


    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)

        return searchBinding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
        initFragment()
        tabSelected()

    }

    override fun onResume() {
        //emptyFragment()
        resultFragment()
        super.onResume()
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.search = this
    }

    private fun initView() {
        presenter = SearchPresenter(this, get(), get())
        presenter.getAlignedJobInterest()
    }

    private fun tabSelected(){
        searchBinding.searchMainTablayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab) {
                val key = JobClassHashMap.hashmap.getKeyFromValue(p0.text.toString())
                presenter.getSearchJobInterest(key)
                key?.let {
                    (searchFrame[1] as SearchResultFragment).apply{
                        arguments = Bundle().apply{
                            putLong("key", key)
                        }
                        (searchFrame[1] as SearchResultFragment).setKey()

                        context?.toastLong("search fragment 1: ${p0.text.toString()} 와 $key")
                    }
                }?: run{
                    context?.toastLong("search fragment 2:${p0.text.toString()} 와 $key")

                }
            }
        })
    }

    override fun checkContentSize(size: Int) {
        when (size) {
            0 -> {
                if (!searchFrame[0].isAdded) emptyFragment()
                else removeEmptyFragment()
                context?.toastLong("empty")

            }
            else -> {
                if (!searchFrame[1].isAdded) {
                    resultFragment()
                    context?.toastLong("flag false")
                } else removeResultFragment()

                    (searchFrame[1] as SearchResultFragment).apply {
                        arguments = Bundle().apply {
                            putBoolean("Flag", true)
                            context?.toastShort("flag true")
                        }
                    }
        }
    }
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
            val intent = Intent(context, SearchDetailActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun attachFragment(id:Int) =
//        childFragmentManager.beginTransaction()
//            .add(R.id.search_main_container, searchFrame[id])
//            .commit()


    private fun resultFragment() =
        childFragmentManager.beginTransaction()
            .add(R.id.search_main_container, searchFrame[1])
            .addToBackStack(null)
            .commit()

    private fun emptyFragment() =
        childFragmentManager.beginTransaction()
            .add(R.id.search_main_container, searchFrame[0])
            .addToBackStack(null)
            .commit()

//    private fun removeFragment(id: Int) =
//        childFragmentManager.beginTransaction()
//            .remove( searchFrame[id])
//            .add(R.id.search_main_container, searchFrame[id])
//            .commit()

    private fun removeResultFragment() =
        childFragmentManager.beginTransaction()
            .remove( searchFrame[1])
            .add(R.id.search_main_container, searchFrame[1])
            .commit()

    private fun removeEmptyFragment() =
        childFragmentManager.beginTransaction()
            .remove( searchFrame[0])
            .add(R.id.search_main_container, searchFrame[0])
            .commit()




    private fun initFragment() {
        searchFrame = ArrayList()
        searchFrame.apply {
            addAll(
                listOf(
                    SearchNoGoalListFragment.newInstance(),
                    SearchResultFragment.newInstance()
                )
            )
        }
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