package com.eroom.domain.customview.bottomsheetAlert

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.domain.R
import com.eroom.domain.customview.bottomsheet.BottomSheetInfo
import com.eroom.domain.databinding.BottomSheetAlertBinding
import com.eroom.domain.globalconst.Consts
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAlertFragment: BottomSheetDialogFragment() {
    lateinit var mBinding: BottomSheetAlertBinding
    lateinit var sheetList: ArrayList<BottomSheetInfo>
    lateinit var mAdapter: BottomSheetAlertAdapter

    val callback: MutableLiveData<Int> = MutableLiveData()

    companion object {
        fun newInstance() =
            BottomSheetAlertFragment()
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme_round

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)


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
        mBinding = BottomSheetAlertBinding.inflate(inflater, container, false)
    }

    fun initView() {
        //mBinding.alertBottomSheet.setBackgroundResource(android.R.color.transparent)
        arguments?.let {
            it.getParcelableArrayList<BottomSheetInfo>(Consts.BOTTOM_SHEET_KEY)?.let { list -> sheetList = list }
            context?.let { ct -> mAdapter = BottomSheetAlertAdapter(sheetList, ct, clicked) }
            mBinding.alertItemSheetRecycler.adapter = mAdapter
            mBinding.alertItemSheetRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private val clicked = { position: Int ->
        callback.value = position
    }
}