package com.eroom.erooja.feature.otherList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.UserSimpleData
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.statusBarColor
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersListBinding

class OtherListActivity : AppCompatActivity(),
    OtherListContract.View {
    lateinit var binding : ActivityOthersListBinding
    lateinit var presenter : OtherListPresenter
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    override fun getAllView(list: UserSimpleData) {
        binding.othersDetailRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OtherListActivity)
            adapter = OthersDetailAdapter(list)
        }
    }

    fun initView(){
        index = intent.getIntExtra(Consts.INDEX, Consts.INDEX_NUM)
        presenter = OtherListPresenter(this)
        presenter.getData(index)
        binding.usernameList.text = intent.getStringExtra(Consts.NAME)
        binding.goalDateTxt.text = intent.getStringExtra(Consts.DATE)

        statusBarColor(this@OtherListActivity, R.color.subLight3)
    }

    fun setUpDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_others_list)
        binding.othersDetail = this@OtherListActivity
    }

    fun back(v: View){
        finish()
    }

}