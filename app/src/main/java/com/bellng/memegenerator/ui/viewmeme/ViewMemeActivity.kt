package com.bellng.memegenerator.ui.viewmeme

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import com.bellng.memegenerator.MemeGeneratorApplication
import com.bellng.memegenerator.R
import com.jakewharton.rxbinding2.view.clicks
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_view_meme.*
import java.io.ByteArrayOutputStream


class ViewMemeActivity : AppCompatActivity() {

    private val viewModelFactory by lazy { (application as MemeGeneratorApplication).appComponent.getViewModelFactory() }
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(ViewMemeViewModel::class.java) }
    private val disposables = CompositeDisposable()

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

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val (resizeWidth, resizeHeight) =
                when (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
                    true -> Pair(displayMetrics.widthPixels - 64, 0)
                    else -> Pair(0, displayMetrics.heightPixels - 64)
                }

        Picasso.with(this)
                .load(intent.getStringExtra(INTENT_MEME_URL))
                .resize(resizeWidth, resizeHeight)
                .into(meme_image, object : Callback {
                    override fun onSuccess() {
                        progress_bar.visibility = View.GONE
                        share_button.visibility = View.VISIBLE
                    }

                    override fun onError() {
                        progress_bar.visibility = View.GONE
                    }
                })
    }

    override fun onResume() {
        super.onResume()

        disposables.addAll(
                share_button.clicks()
                        .map { _ -> ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED }
                        .subscribe(viewModel::onShareButtonClicked))

        disposables.addAll(
                viewModel.startShareImageWithPermissionRequest()
                        .subscribe { shareImageWithPermissionRequest() },
                viewModel.startShareImage()
                        .subscribe { shareImage() })
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    private fun shareImage() {
        if (meme_image.width == 0 || meme_image.height == 0) return

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.putExtra(Intent.EXTRA_STREAM, getImageUri())
        startActivity(Intent.createChooser(intent, "so dank"))
    }

    private fun getBitmap(): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(meme_image.width, meme_image.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        meme_image.background?.draw(canvas)
        meme_image.draw(canvas)
        return returnedBitmap
    }

    private fun getImageUri(): Uri {
        val bytes = ByteArrayOutputStream()
        val bitmap = getBitmap()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "meme", null)
        return Uri.parse(path)
    }

    private fun shareImageWithPermissionRequest() {

    }
}
