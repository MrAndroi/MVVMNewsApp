package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.viewModels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article.*

@AndroidEntryPoint
class ArticleFragment:Fragment(R.layout.fragment_article) {

    val viewModel:NewsViewModel by viewModels()
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article

         webView.apply {
             webViewClient = WebViewClient()
             loadUrl(article.url)
         }

        fab.setOnClickListener {
            viewModel.insertNews(args.article)
            Snackbar.make(it,"Saved To Favourates",Snackbar.LENGTH_SHORT).show()
        }


    }
}