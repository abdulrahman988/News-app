package com.abdulrahman.newsapp.network

import com.abdulrahman.newsapp.models.NewsResponse
import com.abdulrahman.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>

    @GET("v2/everyThing")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String ,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>


    companion object{
        var retrofitService: ApiService? = null
        fun getInstance() : ApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }
    }

}