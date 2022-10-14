package com.example.ui.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint






@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<AuthViewModel>()
    private var urlFinished = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressbar.visible(false)

        val bundle: Bundle = intent.extras!!
        val token = bundle.get("token")
        val body = bundle.get("responseBody")
        val clientId = bundle.get("clientId").toString()
        val pass = bundle.get("password").toString()
        val encr = bundle.get("encr").toString()

        loadPage()
        binding.swipeRefresh.setOnRefreshListener { loadPage() }
    }


    @SuppressLint("SetJavaScriptEnabled")
    fun loadPage() {
        val webSettings: WebSettings = binding.webViewId.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        //webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.setAppCachePath(applicationContext.cacheDir.absolutePath)
        binding.webViewId.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.setSupportZoom(true)

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)

        webSettings.setSupportMultipleWindows(true)

        binding.webViewId.setBackgroundColor(Color.TRANSPARENT)

        val url = viewModel.getPartnerSite(applicationContext)
        binding.webViewId.loadDataWithBaseURL(url, "", "text/html", "utf-8", null)


        val bundle: Bundle = intent.extras!!
        val token = bundle.get("token")
        val body = bundle.get("responseBody")

        binding.webViewId.webChromeClient = WebChromeClient()
        binding.webViewId.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String) {
                super.onPageFinished(view, url)
                Log.d("onPageFinished", "onPageFinished")

                binding.swipeRefresh.isRefreshing = false

                if (urlFinished != url) {
                    Log.d("onPageFinished", "urlFinished != url")

                    binding.webViewId.evaluateJavascript(
                        "window.localStorage.setItem('token', '$token');",
                        null
                    )

                    binding.webViewId.evaluateJavascript(
                        "window.localStorage.setItem('partner-user', '$body');",
                        null
                    )
                }
                urlFinished = url
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("onPageStarted", "onPageStarted")


                if (urlFinished != url) {
                    Log.d("onPageFinished", "urlFinished != url")

                    binding.webViewId.evaluateJavascript(
                        "window.localStorage.setItem('token', '$token');",
                        null
                    )

                    binding.webViewId.evaluateJavascript(
                        "window.localStorage.setItem('partner-user', '$body');",
                        null
                    )
                }
                urlFinished = url
            }

    
        }


        binding.webViewId.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d("onProgressChanged", "onProgressChanged")


                if (urlFinished != url) {
                    Log.d("onPageFinished", "urlFinished != url")

                    binding.webViewId.evaluateJavascript(
                        "window.localStorage.setItem('token', '$token');",
                        null
                    )

                    binding.webViewId.evaluateJavascript(
                        "window.localStorage.setItem('partner-user', '$body');",
                        null
                    )
                }
                urlFinished = url
            
            }


            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                Log.d(
                    "MyApplication", consoleMessage!!.message() + " -- From line "
                            + consoleMessage.lineNumber() + " of "
                            + consoleMessage.sourceId()
                )

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.webViewId.evaluateJavascript(
                        "javascript:window.localStorage.getItem('token')"
                    ) { s -> Log.e("OnRecieve token", s) }
                }, 10000)


                binding.webViewId.evaluateJavascript(
                    "javascript:window.localStorage.getItem('partner-user')"
                ) { s -> Log.e("OnRecieve partner-user", s) }
                return super.onConsoleMessage(consoleMessage)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                window.setTitle(title)
            }
        }

        binding.webViewId.loadUrl(url)
    }


    override fun onBackPressed() {
        if (binding.webViewId.canGoBack()) {
            binding.webViewId.goBack()
        } else {
            super.onBackPressed()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        val parent: ViewParent = binding.webViewId.parent
        (parent as ViewGroup).removeView(binding.webViewId)
        binding.webViewId.stopLoading()
        binding.webViewId.settings.javaScriptEnabled = false
        binding.webViewId.clearHistory()
        binding.webViewId.destroyDrawingCache()
        binding.webViewId.removeAllViews()
        binding.webViewId.destroy()
    }

}