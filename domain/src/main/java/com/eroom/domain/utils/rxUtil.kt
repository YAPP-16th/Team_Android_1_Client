package com.eroom.domain.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

infix fun Disposable.addTo(compositeDisposable: CompositeDisposable) = compositeDisposable.add(this)