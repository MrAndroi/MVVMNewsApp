package com.androiddevs.mvvmnewsapp.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.ui.models.Article

@Database(entities = [Article::class],version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase :RoomDatabase(){

    abstract fun getNewsDoa() : NewsDoa
}