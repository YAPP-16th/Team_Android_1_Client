package com.eroom.domain.customview.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.domain.databinding.BottomSheetBinding
import com.eroom.domain.globalconst.Consts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {
    lateinit var mBinding: BottomSheetBinding
    lateinit var sheetList: ArrayList<BottomSheetInfo>
    lateinit var mAdapter: BottomSheetAdapter

    val callback: MutableLiveData<Int> = MutableLiveData()

    companion object {
        fun newInstance() =
            BottomSheetFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return mBinding.root
    }

    fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mBinding = BottomSheetBinding.inflate(inflater, container, false)
    }

    fun initView() {
        arguments?.let {
            it.getParcelableArrayList<BottomSheetInfo>(Consts.BOTTOM_SHEET_KEY)?.let { list -> sheetList = list }
            context?.let { ct -> mAdapter = BottomSheetAdapter(sheetList, ct, clicked) }
            mBinding.itemSheetRecycler.adapter = mAdapter
            mBinding.itemSheetRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private val clicked = { position: Int ->
        callback.value = position
    }
}