package com.eroom.erooja.feature.addGoal.newGoalPage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.eroom.erooja.R
import com.eroom.erooja.databinding.FragmentGoalListBinding


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GoalListFragment.OnListFragmentInteractionListener] interface.
 */
class GoalListFragment : Fragment() {
    private lateinit var goalListBinding: FragmentGoalListBinding
    //private var goalList: MutableLiveData<List<String>> = MutableLiveData()
    private lateinit var goalList: ArrayList<String>

    companion object {
        @JvmStatic
        fun newInstance() = GoalListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goal_list, container, false)


        setUpDataBinding(inflater, container)
        initView()
        return goalListBinding.root
    }

    //    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnListFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        goalListBinding = FragmentGoalListBinding.inflate(inflater,container,false)
        goalListBinding.fragment = this
    }

    private fun initView() {
        goalList = ArrayList()
        if(goalList.isEmpty()) {
            goalList.add("")
        }
        goalListBinding.goalListRecycler.adapter = GoalAdapter(goalList as ArrayList<String>)
        goalListBinding.goalListRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     *
//     *
//     * See the Android Training lesson
//     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
//     * for more information.
//     */
//    interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onListFragmentInteraction(item: DummyItem?)
//    }

}
