package com.abdulrahman.newsapp.database

import android.content.Context
import androidx.room.*
import com.abdulrahman.newsapp.dao.ArticleDao
import com.abdulrahman.newsapp.models.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

        companion object {
            @Volatile
            private var instance: ArticleDatabase? = null

            fun getInstance(context: Context): ArticleDatabase? {
                if (instance == null) {
                    synchronized(ArticleDatabase::class) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ArticleDatabase::class.java, "article.db"
                        ).build()
                    }
                }
                return instance
            }
        }


}



