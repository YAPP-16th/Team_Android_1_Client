package com.eroom.erooja.feature.otherList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.statusBarColor
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityOthersListBinding
import org.koin.android.ext.android.get

class OtherListActivity : AppCompatActivity(),
    OtherListContract.View {
    lateinit var binding : ActivityOthersListBinding
    lateinit var presenter : OtherListPresenter
    var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    override fun setAllView(todoList: ArrayList<MinimalTodoListDetail>) {
        binding.othersDetailRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OtherListActivity)
            adapter = OthersDetailAdapter(todoList)
        }
    }

    fun initView(){
        presenter = OtherListPresenter(this, get())
        presenter.getData(intent.getStringExtra(Consts.UID), intent.getLongExtra(Consts.GOAL_ID, -1))
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