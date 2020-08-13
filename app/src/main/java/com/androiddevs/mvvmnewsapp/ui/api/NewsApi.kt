package com.androiddevs.mvvmnewsapp.ui.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.androiddevs.mvvmnewsapp.ui.models.NewsResponse

val BASE_URL = "https://newsapi.org"
val API_KEY="3ed0d3a9b0ee4539a957b23a12e61abe"

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews (
        @Query("country") countryCode : String="us",
        @Query("page") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String= API_KEY
    ) : Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews (
        @Query("q") keyWord : String,
        @Query("page") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String= API_KEY
    ) : Response<NewsResponse>

}