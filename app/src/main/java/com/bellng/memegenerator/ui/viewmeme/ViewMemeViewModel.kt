package com.bellng.memegenerator.ui.viewmeme

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by bellng on 29/7/17.
 */
class ViewMemeViewModel : ViewModel() {

    private val shareImageSubject = PublishSubject.create<Unit>()
    private val shareImageWithPermissionsSubject = PublishSubject.create<Unit>()

    fun startShareImage(): Observable<Unit> = shareImageSubject

    fun startShareImageWithPermissionRequest(): Observable<Unit> = shareImageWithPermissionsSubject

    fun onShareButtonClicked(permissionGranted: Boolean) {
        when (permissionGranted) {
            true -> shareImageSubject.onNext(Unit)
            else -> shareImageWithPermissionsSubject.onNext(Unit)
        }

    }

}