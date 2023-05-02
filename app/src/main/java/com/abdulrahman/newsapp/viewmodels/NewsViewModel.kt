package com.abdulrahman.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abdulrahman.newsapp.models.Article
import com.abdulrahman.newsapp.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {
    fun saveArticle(article: Article){
        viewModelScope.launch {
            newsRepository.updateAndInsert(article)
        }
    }

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }

    fun getSavedNews():LiveData<List<Article>>{
        return  newsRepository.getSavedNews()

    }

    fun getAllArticle(): LiveData<PagingData<Article>>{
        return newsRepository.getAllArticle().cachedIn(viewModelScope)

    }

    fun searchAllNews(q: String) :LiveData<PagingData<Article>>{
        return newsRepository.searchAllNews(q).cachedIn(viewModelScope)
    }

     fun articleSaved(articleUrl: String): LiveData<List<Article>>{
        return newsRepository.articleSaved(articleUrl)

    }
}