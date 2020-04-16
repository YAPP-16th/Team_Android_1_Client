package com.eroom.erooja.feature.addGoal.newGoalPage

import android.os.Bundle

import android.view.KeyEvent

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentGoalListBinding


class GoalListFragment : Fragment(), TextView.OnEditorActionListener {
    private lateinit var goalListBinding: FragmentGoalListBinding

    val goalList: MutableLiveData<ArrayList<String>> = MutableLiveData(ArrayList())
    var goalListCheck: MutableLiveData<Boolean> = MutableLiveData(false)

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
        goalListBinding.goalListRecycler.adapter = GoalAdapter(goalList.value!!)
        goalListBinding.goalListRecycler.layoutManager = LinearLayoutManager(requireContext())
        goalListBinding.goalContentEdittext.setOnEditorActionListener(this)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (event == null || event.action != KeyEvent.ACTION_DOWN) {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                var len = v?.text?.trim()?.length
                if (len != null) {
                    if (len < 1) {
                        context?.toastShort("한 글자 이상 입력해주세요")
                        if (goalList.value?.size == 0) {
                            goalListBinding.goalListSizeErrorTextview.visibility = View.VISIBLE
                        }
                        return false
                    } else {
                        val temp = goalList.value ?: ArrayList()
                        val temp2 = temp.apply { add(v?.text.toString().trim()) }
                        this.goalList.value = temp2

                        goalListCheck.value = goalList.value?.size!! > 0
                        goalListBinding.goalListSizeErrorTextview.visibility = View.INVISIBLE

                        v?.text = ""
                        goalListBinding.goalListRecycler.adapter?.notifyDataSetChanged()

                        v?.post(kotlinx.coroutines.Runnable {
                            v.isFocusableInTouchMode = true
                            v.requestFocus()
                        })
                    }
                }
            }
            return false
        }
        return true
    }

}
