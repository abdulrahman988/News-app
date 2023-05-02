package com.abdulrahman.newsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abdulrahman.newsapp.models.Article
import com.abdulrahman.newsapp.network.ApiService
import com.abdulrahman.newsapp.utils.Constants
import java.io.IOException

class SearchNewsPagingSource (
    private val apiService: ApiService,
    val q: String
) : PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {

            val position = params.key ?: 1
            val response = apiService.searchForNews(q, position, Constants.API_KEY)
            val articles = response.body()!!.articles
            val endOfPaginationReached = articles.isEmpty()

            LoadResult.Page(
                data = articles,
                prevKey = if(position > 1 ) position - 1 else null,
                nextKey = if (endOfPaginationReached) null else position + 1 )
        }catch (e: IOException){
            return LoadResult.Error(e)
        }
    }



    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }
}