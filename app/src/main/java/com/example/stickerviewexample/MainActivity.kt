package com.example.stickerviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.stickerviewexample.databinding.ActivityMainBinding
import com.example.stickerviewexample.sticker.StickerImageView
import com.example.stickerviewexample.sticker.StickerView
import com.example.stickerviewexample.sticker.StickerWebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )

        val stickerWebView = StickerWebView(this)
        stickerWebView.addWebView(this, "https://www.google.com")
        binding.flSticker.addView(stickerWebView)
    }
}