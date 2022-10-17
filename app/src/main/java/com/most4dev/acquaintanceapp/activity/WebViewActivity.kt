package com.most4dev.acquaintanceapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import com.most4dev.acquaintanceapp.Config
import com.most4dev.acquaintanceapp.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        baseInitOutcomeMeal(webView)
        overLoadView(webView)
    }

    private fun baseInitOutcomeMeal(dishHaveView: WebView){
        CookieManager.getInstance().setAcceptThirdPartyCookies(dishHaveView, true)
        dishHaveView.settings.allowFileAccess = true
        dishHaveView.isSaveEnabled = true
        dishHaveView.isFocusable = true
        dishHaveView.settings.mixedContentMode = 0
        dishHaveView.settings.databaseEnabled = true
        dishHaveView.settings.loadWithOverviewMode = true
        dishHaveView.webViewClient = WebViewClient()
        dishHaveView.webChromeClient = WebChromeClient()
        dishHaveView.settings.loadsImagesAutomatically = true
        dishHaveView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        dishHaveView.settings.allowContentAccess = true
        dishHaveView.settings.javaScriptEnabled = true
        dishHaveView.settings.useWideViewPort = true
        dishHaveView.settings.javaScriptCanOpenWindowsAutomatically = true
        dishHaveView.settings.javaScriptCanOpenWindowsAutomatically = true
        CookieManager.getInstance().setAcceptCookie(true)
        dishHaveView.settings.domStorageEnabled = true
        dishHaveView.isFocusableInTouchMode = true
    }

    private fun overLoadView(webView: WebView) {
        webView.loadUrl(
            Config.webViewURL
        )
    }


}