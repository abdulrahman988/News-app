package com.abdulrahman.newsapp.repositories

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.abdulrahman.newsapp.database.ArticleDatabase
import com.abdulrahman.newsapp.models.Article
import com.abdulrahman.newsapp.network.ApiService
import com.abdulrahman.newsapp.paging.BreakingNewsPagingSource
import com.abdulrahman.newsapp.paging.SearchNewsPagingSource

class NewsRepository(
    val database: ArticleDatabase
    ) {
    suspend fun updateAndInsert(article: Article): Long{
       return database.getArticleDao().updateAndInsert(article)
    }

    suspend fun deleteArticle(article: Article){
        database.getArticleDao().deleteArticle(article)
    }

    fun getSavedNews():LiveData<List<Article>>{
        return database.getArticleDao().getSavedArticles()

    }

     fun articleSaved(articleUrl: String): LiveData<List<Article>> {
        return database.getArticleDao().articleSaved(articleUrl)
    }

    fun getAllArticle(): LiveData<PagingData<Article>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                BreakingNewsPagingSource(apiService = ApiService.getInstance())
            }, initialKey = 1
        ).liveData
    }
    fun searchAllNews(q: String): LiveData<PagingData<Article>>{
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                SearchNewsPagingSource(apiService = ApiService.getInstance(),q)
            }, initialKey = 1
        ).liveData
    }
}