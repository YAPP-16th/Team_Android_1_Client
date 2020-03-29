package com.eroom.erooja.feature.main

import io.reactivex.disposables.Disposable

interface ExamContract {
    interface View {
    }

    interface Presenter {
        val view: View
        fun testGet(): Disposable
    }
}