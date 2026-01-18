
package com.karaokebh.remote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class MainActivity : AppCompatActivity() {
    private lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        web = findViewById(R.id.web)
        setupWebView()
        loadRemote()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val s: WebSettings = web.settings
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.databaseEnabled = true
        s.cacheMode = WebSettings.LOAD_DEFAULT
        s.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(s, WebSettingsCompat.FORCE_DARK_AUTO)
        }
        web.webViewClient = object : WebViewClient() {}
        web.webChromeClient = WebChromeClient()
    }

    private fun getSavedUrl(): String {
        val prefs = getSharedPreferences("karaoke", MODE_PRIVATE)
        return prefs.getString("remoteUrl", "http://192.168.0.100:8787/remote.html") ?: "http://192.168.0.100:8787/remote.html"
    }

    private fun saveUrl(url: String) {
        getSharedPreferences("karaoke", MODE_PRIVATE).edit().putString("remoteUrl", url.trim()).apply()
    }

    private fun loadRemote() {
        web.loadUrl(getSavedUrl())
    }

    override fun onBackPressed() {
        if (this::web.isInitialized && web.canGoBack()) web.goBack() else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload -> { loadRemote(); true }
            R.id.action_set_url -> { showSetUrlDialog(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSetUrlDialog() {
        val input = android.widget.EditText(this)
        input.hint = "http://<IP>:8787/remote.html"
        input.setText(getSavedUrl())
        AlertDialog.Builder(this)
            .setTitle("Configurar URL do remoto")
            .setMessage("Informe o endereço do host (PC):")
            .setView(input)
            .setPositiveButton("Salvar") { d, _ ->
                val url = input.text?.toString()?.trim()
                if (!url.isNullOrEmpty()) {
                    saveUrl(url)
                    loadRemote()
                }
                d.dismiss()
            }
            .setNegativeButton("Cancelar") { d, _ -> d.dismiss() }
            .show()
    }
}
