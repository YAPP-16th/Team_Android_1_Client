package com.eroom.erooja.feature.filter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.data.response.JobGroupAndClassResponse
import com.eroom.domain.globalconst.Consts
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityFilterBinding
import com.eroom.erooja.dialog.EroojaDialogActivity
import com.eroom.erooja.singleton.JobClassHashMap.hashmap
import org.koin.android.ext.android.get
import timber.log.Timber


class FilterActivity : AppCompatActivity(), FilterContract.View {
    private lateinit var presenter: FilterPresenter
    private lateinit var filterBinding: ActivityFilterBinding
    private lateinit var mAdapter: JobGroupAdapter

    val classCheck: ObservableField<Boolean> = ObservableField(false)
    private val selectedId: ArrayList<Long> = ArrayList()
    private lateinit var interestNum : ArrayList<Long>
    private var dialogFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter = FilterPresenter(this, get(), get())

    }

    private fun setUpDataBinding() {
        filterBinding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        filterBinding.activity = this
    }

    private fun initView() {
        presenter.getJobGroups()

        val settingTxt: String? = intent.getStringExtra("JOB_CLASS_CHANGE")
        settingTxt?.let{
            filterBinding.filterText.text = settingTxt
            filterBinding.closeButton.setImageResource(R.drawable.ic_icon_navi_arrow_left)
        }

        if(intent.getIntExtra(Consts.SETTING_REQUEST,1) == Consts.SETTING_REQUEST_NUM){
            filterBinding.resetText.text = resources.getText(R.string.save_txt)
        }
    }

    override fun reRequestClassByGroup(jobGroupList: ArrayList<com.eroom.data.entity.JobGroup>) =
        jobGroupList.map {
            it.id //직군 (디자인 or 개발) 불러오기
        }.toList().let {
            presenter.getJobGroupAndClasses(it)
        }

    //직무 직군 버튼 다 가져오는 함수임
    override fun updateJobGroupAndClass(result: List<JobGroupAndClassResponse>) {
        interestNum = intent.getSerializableExtra("search") as ArrayList<Long>
        for(i in interestNum){
            selectedId.add(i)
        }

        mAdapter = JobGroupAdapter(this, result, selectedId, itemClick)
        filterBinding.jobGroupRecycler.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        checkSelect()
    }

    private val itemClick = { id: Long, preState: Boolean ->
        if (preState)
            selectedId.remove(id)
        else
            selectedId.add(id)
        checkSelect()
        mAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = data?.getBooleanExtra(Consts.DIALOG_RESULT, false) //확인 or 취소

        if(resultCode == 6000){
            if (result!!) { //if ok (when request code ; 1200, 1300 )
                completeButtonClicked()
            }
        }
    }

    fun completeButtonClicked() {
        val result1 = intent.putExtra("selectedId",selectedId)
        val result2 = intent.putExtra("HashMap", hashmap)
        setResult(1000, result1)
        setResult(1000, result2)

        if(classCheck.get()!!) finish()

    }

    fun resetFilterButtonClicked(){
        selectedId.clear()
        mAdapter.notifyDataSetChanged()
        checkSelect()
    }

    fun closeButtonClicked() {
        showAlert()
    }

    override fun onBackPressed() {
        showAlert()
    }

    private fun showAlert(){
        if(selectedId.isNullOrEmpty()){
            //필터에 1개 이상의 직무를 지정해주세요.
            startActivityForResult(
                Intent(
                    this,
                    EroojaDialogActivity::class.java
                ).apply {
                    putExtra(Consts.DIALOG_TITLE, "")
                    putExtra(
                        Consts.DIALOG_CONTENT,
                        "필터에 1개 이상의 직무를 지정해주세요."
                    )
                    putExtra(Consts.DIALOG_CONFIRM, true)
                    putExtra(Consts.DIALOG_CANCEL, false)
                }, 5000)
        }
        else {
            //변경 내용이 있는지 판단
            dialogFlag = false
            if (selectedId.size != interestNum.size) {
                dialogFlag = true
            } else {
                selectedId.sort()
                interestNum.sort()
                repeat(selectedId.size) {
                    dialogFlag = selectedId[it] != interestNum[it]
                }
            }
            //entry point : setting gragment
            if (intent.getIntExtra(Consts.SETTING_REQUEST, 1) == Consts.SETTING_REQUEST_NUM) {
                if (dialogFlag) {
                    startActivityForResult(
                        Intent(
                            this,
                            EroojaDialogActivity::class.java
                        ).apply {
                            putExtra(Consts.DIALOG_TITLE, "")
                            putExtra(
                                Consts.DIALOG_CONTENT,
                                "변경된 관심 직무 내역을 저장하시겠어요?"
                            )
                            putExtra(Consts.DIALOG_CONFIRM, true)
                            putExtra(Consts.DIALOG_CANCEL, true)
                        }, 1300
                    )
                } else finish()

            }
            //entry point : search fragment
            else{
                //변경된 필터 내용을 저장하시겠어요?
                if (dialogFlag) {
                    startActivityForResult(
                        Intent(
                            this,
                            EroojaDialogActivity::class.java
                        ).apply {
                            putExtra(Consts.DIALOG_TITLE, "")
                            putExtra(
                                Consts.DIALOG_CONTENT,
                                "변경된 필터 내용을 저장하시겠어요?"
                            )
                            putExtra(Consts.DIALOG_CONFIRM, true)
                            putExtra(Consts.DIALOG_CANCEL, true)
                        }, 1200
                    )
                } else finish()
            }
        }

    }

    private fun checkSelect() {
        classCheck.set(selectedId.size != 0)
    }

    override fun onDestroy() {
        presenter.onCleared()
        super.onDestroy()
    }
}
