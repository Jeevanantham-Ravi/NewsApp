package com.news.online.di

import android.app.Application
import androidx.room.Room
import com.news.online.data.local.NewsDao
import com.news.online.data.local.NewsDatabase
import com.news.online.data.local.NewsTypeConvertor
import com.news.online.data.manager.LocalUserMangerImpl
import com.news.online.data.remote.NewsApi
import com.news.online.data.repository.NewsRepositoryImpl
import com.news.online.domain.manager.LocalUserManager
import com.news.online.domain.repository.NewsRepository
import com.news.online.domain.usecases.app_entry.AppEntryUseCases
import com.news.online.domain.usecases.app_entry.ReadAppEntry
import com.news.online.domain.usecases.app_entry.SaveAppEntry
import com.news.online.domain.usecases.news.DeleteArticle
import com.news.online.domain.usecases.news.GetArticle
import com.news.online.domain.usecases.news.GetArticles
import com.news.online.domain.usecases.news.GetNews
import com.news.online.domain.usecases.news.NewsUseCases
import com.news.online.domain.usecases.news.SearchNews
import com.news.online.domain.usecases.news.UpsertArticle
import com.news.online.util.Constants.BASE_URL
import com.news.online.util.Constants.NEWS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserMangerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager), saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository, newsDao: NewsDao): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao = newsDao),
            deleteArticle = DeleteArticle(newsDao),
            getArticles = GetArticles(newsDao),
            getArticle = GetArticle(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE,
        ).addTypeConverter(NewsTypeConvertor()).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao
}