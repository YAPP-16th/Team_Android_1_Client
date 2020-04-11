package com.eroom.erooja.feature.goalDetail.othersList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersListBinding

class OthersDetailActivity :AppCompatActivity(), OthersDetailContract.View {
    lateinit var binding : ActivityOthersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_list)
        setUpDataBinding()
        initView()
    }

    override fun getAllView(list: UserSimpleData) {
        binding.othersDetailRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OthersDetailActivity)
            adapter = OthersDetailAdapter(list)
        }
    }

    fun initView(){
        var presenter = OthersDetailPresenter(this)
        presenter.getData()
    }

    fun setUpDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_others_list)
        binding.othersDetail = this@OthersDetailActivity
    }

}