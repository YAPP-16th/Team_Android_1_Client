package com.eroom.erooja.feature.search


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.eroom.erooja.databinding.FragmentSearchBinding

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var searchBinding: FragmentSearchBinding
    private lateinit var presenter: SearchPresenter

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = SearchPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return searchBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.fragment = this
    }

    private fun initView() {

    }

}
