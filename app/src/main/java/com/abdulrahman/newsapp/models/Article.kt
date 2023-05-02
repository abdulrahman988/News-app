package com.abdulrahman.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles" )
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
) : Serializable{


    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass){
            return false
        }
        other as Article

        if (id != other.id){
            return false
        }
        if (author != other.author){
            return false
        }
        if (content != other.content){
            return false
        }
        if (description != other.description){
            return false
        }
        if (publishedAt != other.publishedAt){
            return false
        }
        if (source?.id != other.source?.id){
            return false
        }
        if (source?.name != other.source?.name){
            return false
        }
        if (title != other.title){
            return false
        }
        if (url != other.url){
            return false
        }
        if (urlToImage != other.urlToImage){
            return false
        }
        return true
    }


}