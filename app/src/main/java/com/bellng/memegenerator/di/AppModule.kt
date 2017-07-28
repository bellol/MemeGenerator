package com.bellng.memegenerator.di

import com.bellng.memegenerator.MemeGeneratorApplication
import com.bellng.memegenerator.ui.GenerateMemeViewModel
import com.bellng.memegenerator.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Created by bellng on 29/7/17.
 */

@Module
class AppModule(val application: MemeGeneratorApplication) {
    @Provides fun provideViewModelFactory(appComponent: AppComponent): ViewModelFactory = ViewModelFactory(appComponent)

    @Provides fun provideDashboardViewModel(): GenerateMemeViewModel = GenerateMemeViewModel()
}