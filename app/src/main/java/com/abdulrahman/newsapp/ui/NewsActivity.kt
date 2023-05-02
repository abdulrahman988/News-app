package com.abdulrahman.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abdulrahman.R
import com.abdulrahman.databinding.ActivityNewsBinding
import com.abdulrahman.newsapp.database.ArticleDatabase
import com.abdulrahman.newsapp.repositories.NewsRepository
import com.abdulrahman.newsapp.viewmodels.NewsViewModel
import com.abdulrahman.newsapp.viewmodels.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val articleDatabase by lazy { ArticleDatabase.getInstance(this) }
    lateinit var viewModel :NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = articleDatabase?.let { NewsRepository(it) }
        val viewModelProviderFactory = newsRepository?.let { NewsViewModelProviderFactory(it) }
        viewModel = viewModelProviderFactory?.let {
            ViewModelProvider(this,
                it
            ).get(NewsViewModel::class.java)
        }!!





        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}