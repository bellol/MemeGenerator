package com.bellng.memegenerator.ui.viewmeme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bellng.memegenerator.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_meme.*

class ViewMemeActivity : AppCompatActivity() {

    companion object {
        const val INTENT_MEME_URL = "memeUrl"
        fun createIntent(context: Context, url: String): Intent {
            val intent = Intent(context, ViewMemeActivity::class.java)
            intent.putExtra(INTENT_MEME_URL, url)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_meme)

        Picasso.with(this)
                .load(intent.getStringExtra(INTENT_MEME_URL))
                .into(meme_image, object : Callback {
                    override fun onSuccess() {
                        progress_bar.visibility = View.GONE
                    }

                    override fun onError() {

                    }
                })
    }
}
