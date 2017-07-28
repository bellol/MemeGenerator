package com.bellng.memegenerator.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bellng.memegenerator.MemeGeneratorApplication
import com.bellng.memegenerator.R

class GenerateMemeActivity : AppCompatActivity() {

    private val viewModelFactory by lazy { (application as MemeGeneratorApplication).appComponent.getViewModelFactory() }
    private val viewModel by lazy { ViewModelProviders.of(this).get(GenerateMemeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_meme)
    }
}
