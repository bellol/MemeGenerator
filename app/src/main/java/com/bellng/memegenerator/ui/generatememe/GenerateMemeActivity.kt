package com.bellng.memegenerator.ui.generatememe

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bellng.memegenerator.MemeGeneratorApplication
import com.bellng.memegenerator.R
import com.bellng.memegenerator.ui.viewmeme.ViewMemeActivity
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_generate_meme.*

class GenerateMemeActivity : AppCompatActivity() {

    private val viewModelFactory by lazy { (application as MemeGeneratorApplication).appComponent.getViewModelFactory() }
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(GenerateMemeViewModel::class.java) }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_meme)
    }

    override fun onResume() {
        super.onResume()

        disposables.addAll(
                viewModel.showViewMemeScreen()
                        .subscribe
                        { url -> startActivity(ViewMemeActivity.createIntent(this, url)) },
                generate_button
                        .clicks()
                        .map { _ -> "http://apimeme.com/meme?meme=${meme_name.text}&top=${top_text.text}&bottom=${bottom_text.text}".replace(" ", "+") }
                        .subscribe(viewModel::onGenerateButtonClicked))
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }
}
