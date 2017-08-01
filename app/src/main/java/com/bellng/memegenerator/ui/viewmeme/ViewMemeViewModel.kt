package com.bellng.memegenerator.ui.viewmeme

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by bellng on 29/7/17.
 */
class ViewMemeViewModel : ViewModel() {

    private val shareImageSubject = PublishSubject.create<Boolean>()

    fun startShareImage(): Observable<Boolean> = shareImageSubject

    fun onShareButtonClicked() = shareImageSubject.onNext(true)
}