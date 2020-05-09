package com.eroom.erooja.feature.otherList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.entity.MinimalTodoListDetail
import com.eroom.domain.globalconst.Consts
import com.eroom.domain.utils.loadDrawable
import com.eroom.domain.utils.loadUrlCenterCrop
import com.eroom.domain.utils.statusBarColor
import com.eroom.domain.utils.toastShort
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

    fun setUpDataBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_others_list)
        binding.othersDetail = this@OtherListActivity
    }

    override fun setAllView(todoList: ArrayList<MinimalTodoListDetail>) {
        binding.othersDetailRecyclerview.apply{
            layoutManager = LinearLayoutManager(this@OtherListActivity)
            adapter = OthersDetailAdapter(todoList)
        }
    }

    fun initView(){
        presenter = OtherListPresenter(this, get(), get())
        presenter.getData(intent.getStringExtra(Consts.UID), intent.getLongExtra(Consts.GOAL_ID, -1))
        presenter.getProfileImage()
        binding.usernameList.text = intent.getStringExtra(Consts.NAME)
        binding.goalDateTxt.text = intent.getStringExtra(Consts.DATE)

        statusBarColor(this@OtherListActivity, R.color.subLight3)
    }

    override fun setProfileImage(imagePath: String?) {
        imagePath?.let{ binding.circleImageView.loadUrlCenterCrop(imagePath)
            this.toastShort(imagePath)}
            ?:run{ binding.circleImageView.loadDrawable(resources.getDrawable(R.drawable.ic_icon_users_blank, null)) }    }

    fun back(v: View){
        finish()
    }

}