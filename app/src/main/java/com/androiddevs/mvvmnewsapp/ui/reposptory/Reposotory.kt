package com.androiddevs.mvvmnewsapp.ui.reposptory

import android.content.Context
import com.androiddevs.mvvmnewsapp.ui.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.ui.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.ui.db.NewsDoa
import com.androiddevs.mvvmnewsapp.ui.models.Article
import javax.inject.Inject

class Reposotory @Inject constructor(val db:ArticleDatabase) {


    suspend fun getBreakingNews(counteryCode:String,pageNum:Int) =
        RetrofitInstance.api.getBreakingNews(counteryCode,pageNum)

    suspend fun searchForNews(keyWord:String,pageNum: Int)=
        RetrofitInstance.api.searchForNews(keyWord,pageNum)

    suspend fun insertNews(article: Article)=
        db.getNewsDoa().insertArticle(article)

    suspend fun deleteNews(article: Article)=
      db.getNewsDoa().deleteArticle(article)

    fun getAllNews()=
        db.getNewsDoa().getAllArticles()

}