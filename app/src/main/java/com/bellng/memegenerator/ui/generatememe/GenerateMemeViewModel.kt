package com.bellng.memegenerator.ui.generatememe

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by bellng on 28/7/17.
 */
class GenerateMemeViewModel : ViewModel() {

    val showViewMemeScreenSubject: PublishSubject<String> = PublishSubject.create()

    fun onGenerateButtonClicked(url: String) = showViewMemeScreenSubject.onNext(url)

    fun showViewMemeScreen(): Observable<String> = showViewMemeScreenSubject

}