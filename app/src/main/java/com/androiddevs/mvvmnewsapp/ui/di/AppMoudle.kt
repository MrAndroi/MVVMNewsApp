package com.androiddevs.mvvmnewsapp.ui.di

import android.app.Application
import androidx.room.Room
import com.androiddevs.mvvmnewsapp.ui.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.ui.db.NewsDoa
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app:Application):ArticleDatabase{
        return Room.databaseBuilder(app,ArticleDatabase::class.java,"article_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDoa(db:ArticleDatabase):NewsDoa{
        return db.getNewsDoa()
    }
}