package com.eroom.erooja.feature.editgoal

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.databinding.FragmentAddTodoListBinding

class AddListFragment : Fragment(), TextView.OnEditorActionListener {
    lateinit var addTodoBinding: FragmentAddTodoListBinding
    private lateinit var mAdapter: AddTodoAdapter

    private val goalItem: ArrayList<String> = ArrayList()

    val goalList: MutableLiveData<ArrayList<String>> = MutableLiveData(ArrayList())
    var goalListCheck: MutableLiveData<Boolean> = MutableLiveData(false)

    val isExistWritingText: ObservableField<Boolean> = ObservableField()
    val writingText: MutableLiveData<String> = MutableLiveData()

    companion object {
        @JvmStatic
        fun newInstance() = AddListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return addTodoBinding.root
    }


    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        addTodoBinding = FragmentAddTodoListBinding.inflate(inflater, container, false)
        addTodoBinding.fragment = this
    }

    private fun initView() {
        loadRecyclerView()
        addTodoBinding.goalListRecycler.layoutManager = LinearLayoutManager(requireContext())
        addTodoBinding.goalContentEdittext.setOnEditorActionListener(this)
        addTodoBinding.goalContentEdittext.doAfterTextChanged {
            isExistWritingText.set(it?.length ?: 0 > 0)
            writingText.value = it.toString()
        }
        requestFocus(addTodoBinding.goalContentEdittext)
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
                            addTodoBinding.goalListSizeErrorTextview.visibility = View.VISIBLE
                        }
                        return false
                    } else {
                        goalItem.add(v.text.toString().trim())
                        this.goalList.value = goalItem

                        goalListCheck.value = goalItem.size > 0
                        addTodoBinding.goalListSizeErrorTextview.visibility = View.INVISIBLE

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
        mAdapter = AddTodoAdapter(goalItem, deleteItem)
        addTodoBinding.goalListRecycler.adapter = mAdapter
    }

    private fun requestFocus(v: View) {
        v.post(kotlinx.coroutines.Runnable {
            v.isFocusableInTouchMode = true
            v.requestFocus()
            (activity as EditGoalActivity).showKeyboard(v as EditText)
        })
    }
}