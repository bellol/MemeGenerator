package com.bellng.memegenerator.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import com.bellng.memegenerator.di.AppComponent
import com.bellng.memegenerator.ui.GenerateMemeViewModel

/**
 * Created by bellng on 29/7/17.
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(appComponent: AppComponent) : ViewModelProvider.Factory {

    private val creators: ArrayMap<Class<*>, () -> ViewModel> = ArrayMap()

    init {
        creators.put(GenerateMemeViewModel::class.java, { appComponent.getGenerateMemeViewModel() })
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T = creators[modelClass]?.invoke() as T
}