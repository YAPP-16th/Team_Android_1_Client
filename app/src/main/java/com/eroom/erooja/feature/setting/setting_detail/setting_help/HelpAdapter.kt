package com.eroom.erooja.feature.setting.setting_detail.setting_help

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemHelpListBinding
import ru.rhanza.constraintexpandablelayout.ExpandableLayout
import ru.rhanza.constraintexpandablelayout.State

class HelpAdapter (val context: Context, val questionList: Array<String>, val answerList: Array<String>):
    RecyclerView.Adapter<HelpAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemHelpListBinding.inflate(inflater, parent, false)
        return ViewHolder(mBinding, mBinding.questionText, mBinding.answerText, mBinding.helpExpandable, mBinding.helpMoreBtn)

    }

    override fun getItemCount(): Int =questionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.helpTxt.text = questionList[position]
        holder.helpDescTxt.text = answerList[position]

        holder.clickBtn.setOnClickListener { holder.layout.toggle() }
        holder.layout.onStateChangeListener = { oldState: State, newState: State ->
            when (newState) {
                State.Expanded -> {
                    holder.clickBtn.background = context.resources.getDrawable(R.drawable.ic_icon_small_arrow_top_orange, null)
                    holder.helpTxt.ellipsize = null
                    holder.helpTxt.maxLines = Integer.MAX_VALUE

                }
                State.Collapsed -> {
                    holder.clickBtn.background = context.resources.getDrawable(R.drawable.ic_icon_small_arrow_bottom_orange, null)
                    holder.helpTxt.ellipsize = TextUtils.TruncateAt.END
                    holder.helpTxt.maxLines = 1
                }

            }
        }
    }


    inner class ViewHolder(
        mBinding : ItemHelpListBinding,
        val helpTxt : TextView,
        val helpDescTxt : TextView,
        val layout : ExpandableLayout,
        var clickBtn : ImageButton
    ) : RecyclerView.ViewHolder(mBinding.root)
}