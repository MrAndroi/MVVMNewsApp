package com.androiddevs.mvvmnewsapp.ui.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.ui.Resuorce
import com.androiddevs.mvvmnewsapp.ui.models.Article
import com.androiddevs.mvvmnewsapp.ui.models.NewsResponse
import com.androiddevs.mvvmnewsapp.ui.reposptory.Reposotory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response


class NewsViewModel @ViewModelInject constructor(val repo: Reposotory):ViewModel() {

    val breakingNewsList:MutableLiveData<Resuorce<NewsResponse>> = MutableLiveData()
    val newsAfterSearch:MutableLiveData<Resuorce<NewsResponse>> = MutableLiveData()
    val savedNews : LiveData<List<Article>>? = null
    private val pageNumber = 1

    init {
        getbreakingNews("us")
        searchForNews("corona")
    }

    fun getbreakingNews(counteryCode:String)= viewModelScope.launch {
        breakingNewsList.postValue(Resuorce.Loading())
        val response = repo.getBreakingNews(counteryCode,pageNumber)
        breakingNewsList.postValue(handleBreakingNewsResponse(response))
    }

    fun searchForNews(keyWord:String)=viewModelScope.launch {
        newsAfterSearch.postValue(Resuorce.Loading())
        val response = repo.searchForNews(keyWord,pageNumber)
        newsAfterSearch.postValue(handleSearchNews(response))

    }

    fun insertNews(article: Article)=viewModelScope.launch {
        repo.insertNews(article)
    }

    fun getAllNews()= repo.getAllNews()

    fun deleteNews(article: Article) =viewModelScope.launch {
        repo.deleteNews(article)
    }

    private fun handleBreakingNewsResponse(response:Response<NewsResponse>):Resuorce<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resuorce.Success(it)
            }
        }
        return Resuorce.Error(response.message())
    }

    private fun handleSearchNews(response: Response<NewsResponse>):Resuorce<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resuorce.Success(it)
            }
        }
        return Resuorce.Error(response.message())
    }

}