package com.eroom.erooja.feature.participants_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.Participant
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityParticipantsListBinding

class ParticipantsListActivity : AppCompatActivity(), ParticipantsListContract.View {
    private lateinit var particiBinding: ActivityParticipantsListBinding
    private lateinit var presenter: ParticipantsListPresenter

    private lateinit var mAdapter: ParticipantListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    fun initPresenter() {
        presenter = ParticipantsListPresenter(this)
    }

    fun setUpDataBinding() {
        particiBinding = DataBindingUtil.setContentView(this, R.layout.activity_participants_list)
        particiBinding.activity = this
    }

    fun initView() {
        val dummy: ArrayList<Participant> = arrayListOf(Participant(1, "dddd", ""), Participant(2, "eee", ""))
        mAdapter = ParticipantListAdapter(presenter.mParticipantDiffCallback, dummy)
        particiBinding.profileRecycler.adapter = mAdapter
        particiBinding.profileRecycler.layoutManager = LinearLayoutManager(this)
    }
}
