package com.bellng.memegenerator

import android.app.Application
import com.bellng.memegenerator.di.AppComponent
import com.bellng.memegenerator.di.DaggerAppComponent

/**
 * Created by bellng on 29/7/17.
 */
class MemeGeneratorApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .build()
    }

}