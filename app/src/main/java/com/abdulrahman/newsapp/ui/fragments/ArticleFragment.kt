package com.abdulrahman.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abdulrahman.R
import com.abdulrahman.databinding.FragmentArticleBinding
import com.abdulrahman.newsapp.ui.NewsActivity
import com.abdulrahman.newsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment() : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        var article = args.article


        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            article.url?.let { loadUrl(it) }
        }

        viewModel.articleSaved(article.url!!).observe(viewLifecycleOwner) { articlesSaved ->
            if (articlesSaved.isEmpty()) {
                binding.fab.setOnClickListener {
                    viewModel.saveArticle(article)
                    binding.fab.setImageResource(R.drawable.ic_baseline_check_24)
                }
            }
        }

    }
}


