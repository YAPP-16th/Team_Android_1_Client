package com.eroom.erooja.feature.exam.activity

import io.reactivex.disposables.Disposable

interface ExamContract {
    interface View {
    }

    interface Presenter {
        val view: View
        fun testGet(): Disposable
    }
}