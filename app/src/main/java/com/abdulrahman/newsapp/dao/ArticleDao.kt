package com.abdulrahman.newsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abdulrahman.newsapp.models.Article

@Dao
interface ArticleDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAndInsert(article: Article): Long


    @Delete
    suspend fun deleteArticle(article: Article)


    @Query("SELECT * FROM articles")
    fun getSavedArticles(): LiveData<List<Article>>


     @Query("SELECT * FROM articles WHERE url = :articleUrl")
     fun articleSaved(articleUrl: String): LiveData<List<Article>>


}