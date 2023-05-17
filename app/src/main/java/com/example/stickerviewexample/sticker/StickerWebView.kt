package com.example.stickerviewexample.sticker

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebView
import androidx.constraintlayout.widget.ConstraintLayout

class StickerWebView : StickerView {

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    fun addWebView(context: Context, url: String) {
        val lp = LayoutParams(
            MATCH_PARENT,
            MATCH_PARENT
        )
        val webView = WebView(context)
        webView.loadUrl(url)
        webView.setOnTouchListener { _, event ->
            if (touchEnabled) {
                onTouchEvent(event)
            }
            false
        }
        lp.setMargins(48, 48, 48, 48) // 오른쪽 아래 여백 설정
        addView(webView, lp)
    }
}