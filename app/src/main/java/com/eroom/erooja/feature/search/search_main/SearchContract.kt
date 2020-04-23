package com.eroom.erooja.feature.search.search_main


interface SearchContract{
    interface View{
       fun setAlignedJobInterest(interest: MutableSet<String>)
    }

    interface Presenter{
        val view: View
        fun getAlignedJobInterest()
    }
}