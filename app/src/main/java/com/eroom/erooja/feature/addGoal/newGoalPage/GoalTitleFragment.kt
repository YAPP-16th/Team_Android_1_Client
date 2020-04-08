package com.eroom.erooja.feature.addGoal.newGoalPage

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.erooja.databinding.FragmentGoalTitleBinding
import com.eroom.erooja.R
import com.jakewharton.rxbinding.widget.RxTextView
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class GoalTitleFragment : Fragment() {
    private lateinit var goalTitleBinding: FragmentGoalTitleBinding
    val goalTitle: MutableLiveData<String> = MutableLiveData()
    var goalTitleCheck: MutableLiveData<Boolean> = MutableLiveData(false)


    companion object {
        @JvmStatic
        fun newInstance() = GoalTitleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return goalTitleBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        goalTitleBinding = FragmentGoalTitleBinding.inflate(inflater, container, false)
        goalTitleBinding.fragment = this
    }

    private fun initView() {
        goalTitleBinding.goalTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val it = s.toString().trim()
                val len = it.length
                goalTitleCheck.value = len > 4
                if (len in 1..4) {
                    goalTitleBinding.goalTitleLengthError.visibility = View.VISIBLE
                } else {
                    goalTitleBinding.goalTitleLengthError.visibility = View.INVISIBLE
                    goalTitle.value = it
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    fun onLayoutClicked() {
        goalTitleBinding.goalTitle.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        context?.let { imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY) }
    }

}
