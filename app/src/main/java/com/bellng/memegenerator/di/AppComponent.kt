package com.bellng.memegenerator.di

import com.bellng.memegenerator.ui.GenerateMemeViewModel
import com.bellng.memegenerator.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bellng on 29/7/17.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun getViewModelFactory(): ViewModelFactory
    fun getGenerateMemeViewModel(): GenerateMemeViewModel
}