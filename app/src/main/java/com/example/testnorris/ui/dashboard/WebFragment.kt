package com.example.testnorris.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testnorris.databinding.FragmentWebBinding


class WebFragment : Fragment() {

    private lateinit var webViewModel: WebViewModel
    private lateinit var webView: WebView
    private var _binding: FragmentWebBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        webViewModel =
            ViewModelProvider(this).get(WebViewModel::class.java)
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView: WebView = binding.webview
        webView.settings.setJavaScriptEnabled(true)
        webView.webViewClient = object : WebViewClient() {
        }
        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
        else
            webView.loadUrl("https://www.icndb.com/api/")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}