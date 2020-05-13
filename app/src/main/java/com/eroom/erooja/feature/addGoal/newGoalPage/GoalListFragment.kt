package com.eroom.erooja.feature.addGoal.newGoalPage

import android.os.Bundle
import android.util.Log

import android.view.KeyEvent

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentGoalListBinding


class GoalListFragment : Fragment(), TextView.OnEditorActionListener {
    private lateinit var goalListBinding: FragmentGoalListBinding
    private lateinit var mAdapter: GoalAdapter

    private var goalItem: ArrayList<String> = ArrayList()

    val goalList: MutableLiveData<ArrayList<String>> = MutableLiveData(ArrayList())
    var goalListCheck: MutableLiveData<Boolean> = MutableLiveData(false)

    val isExistWritingText: ObservableField<Boolean> = ObservableField()
    val writingText: MutableLiveData<String> = MutableLiveData()

    companion object {
        @JvmStatic
        fun newInstance() = GoalListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goal_list, container, false)
        setUpDataBinding(inflater, container)
        initView()
        return goalListBinding.root
    }


    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        goalListBinding = FragmentGoalListBinding.inflate(inflater, container, false)
        goalListBinding.fragment = this
    }

    private fun initView() {
        loadRecyclerView()
        goalListBinding.goalListRecycler.layoutManager = LinearLayoutManager(requireContext())
        goalListBinding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < 20) {
                goalListBinding.blurView.visibility = View.INVISIBLE
            } else {
                goalListBinding.blurView.visibility = View.VISIBLE
            }
        }
        goalListBinding.goalContentEdittext.setOnEditorActionListener(this)
        goalListBinding.goalContentEdittext.doAfterTextChanged {
            isExistWritingText.set(it?.length ?: 0 > 0)
            writingText.value = it.toString()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null || event.action != KeyEvent.ACTION_DOWN) {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                val len = v?.text?.trim()?.length
                if (len != null) {
                    if (len < 1) {
                        requestFocus(v)
                        context?.toastShort("한 글자 이상 입력해주세요")
                        if (goalItem.size == 0) {
                            goalListBinding.goalListSizeErrorTextview.visibility = View.VISIBLE
                        }
                        return false
                    } else {
                        goalItem.add(v.text.toString().trim())
                        this.goalList.value = goalItem

                        goalListCheck.value = goalItem.size > 0
                        goalListBinding.goalListSizeErrorTextview.visibility = View.INVISIBLE

                        v.text = ""
                        loadRecyclerView()
                        requestFocus(v)
                    }
                }
            }
            return false
        }
        return true
    }

    private val deleteItem = { position: Int ->
        goalItem.removeAt(position)
        this.goalList.value = goalItem
        loadRecyclerView()
    }

    private fun loadRecyclerView() {
        arguments?.let{
            goalItem = it.getStringArrayList("todolist") as ArrayList<String> }
        if(goalItem[0].isNullOrEmpty()) goalItem.clear()
        mAdapter = GoalAdapter(goalItem, deleteItem)
        goalListBinding.goalListRecycler.adapter = mAdapter
    }

    private fun requestFocus(v: View) {
        v.post(kotlinx.coroutines.Runnable {
            v.isFocusableInTouchMode = true
            v.requestFocus()
        })
    }

}