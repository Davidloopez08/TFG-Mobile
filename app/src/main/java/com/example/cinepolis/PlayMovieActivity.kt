package com.example.cinepolis

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

class PlayMovieActivity : AppCompatActivity() {

    private lateinit var uri: Uri
    private var mPlayer: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playmovie)
        val url: String = intent.getStringExtra("url").toString()

        // Establecer la pantalla en modo completo sin usar WindowManager
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN

        // Establecer orientación de la pantalla a horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        playerView = findViewById(R.id.video_view)
        uri = Uri.parse(url)
        initPlayer()
    }

    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(this).build() // ExoPlayer actualizado
        playerView.player = mPlayer
        mPlayer!!.playWhenReady = true
        val mediaSource = buildMediaSource()
        mPlayer!!.setMediaSource(mediaSource)
        mPlayer!!.prepare()
    }

    private fun buildMediaSource(): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))  // Actualización a MediaItem
    }

    private fun releasePlayer() {
        mPlayer?.release()
        mPlayer = null
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    override fun onBackPressed() {
        mPlayer?.run {
            playWhenReady = false
            release()
        }
        mPlayer = null
        super.onBackPressed()
    }
}
