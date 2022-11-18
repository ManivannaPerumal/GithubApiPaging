package assessment.narayanagroup.githubapisearch.presentation

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import assessment.narayanagroup.githubapisearch.data.model.Repository
import assessment.narayanagroup.githubapisearch.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    lateinit var wProgress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val webUrl = intent.getStringExtra("readmeUrl")

        if(webUrl.isNullOrEmpty()){
            finish()
            Toast.makeText(this@WebViewActivity, "Url not found ,try Again!", Toast.LENGTH_SHORT).show()
        }
        else{
            wProgress = ProgressDialog(this)
            wProgress.setTitle("Loading....")
            wProgress.setCancelable(false)
            wProgress.show()

            loadUrl(webUrl,binding)
        }

    }

    private fun loadUrl(url: String, binding: ActivityWebviewBinding){
        // javascript enable for webview
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.allowContentAccess = true
        binding.webView.settings.domStorageEnabled = true

        binding.webView.loadUrl(url)

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                wProgress.dismiss()
            }
        }
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }
}