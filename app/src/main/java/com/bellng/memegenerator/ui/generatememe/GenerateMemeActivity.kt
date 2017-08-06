package com.bellng.memegenerator.ui.generatememe

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.bellng.memegenerator.MemeGeneratorApplication
import com.bellng.memegenerator.R
import com.bellng.memegenerator.ui.viewmeme.ViewMemeActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChangeEvents
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_generate_meme.*


class GenerateMemeActivity : AppCompatActivity() {

    private val viewModelFactory by lazy { (application as MemeGeneratorApplication).appComponent.getViewModelFactory() }
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(GenerateMemeViewModel::class.java) }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_meme)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.memes))
        searchable_spinner.adapter = adapter
        searchable_spinner.setPositiveButton("OK")
    }

    override fun onResume() {
        super.onResume()

        disposables.addAll(
                viewModel.showViewMemeScreen()
                        .subscribe { url -> startActivity(ViewMemeActivity.createIntent(this, url)) },
                top_text.textChangeEvents()
                        .subscribe { event ->
                            when (event.text().isNullOrEmpty()) {
                                true -> top_text.hint = "top text"
                                else -> top_text.hint = ""
                            }
                        },
                bottom_text.textChangeEvents()
                        .subscribe { event ->
                            when (event.text().isNullOrEmpty()) {
                                true -> bottom_text.hint = "bottom text"
                                else -> bottom_text.hint = ""
                            }
                        },
                generate_button.clicks()
                        .map { _ -> generateUrl() }
                        .subscribe(viewModel::onGenerateButtonClicked))
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    private fun generateUrl(): String {
        var url = "http://apimeme.com/meme?meme=${searchable_spinner.selectedItem}"

        if (top_text.text.toString().isNotEmpty()) {
            url += "&top=${top_text.text}"
        }

        if (bottom_text.text.toString().isNotEmpty()) {
            url += "&bottom=${bottom_text.text}"
        }

        return url.replace(" ", "+")
    }
}
