package ru.godsonpeya.atmosphere.di

import android.content.Context
import androidx.room.Room
import ru.godsonpeya.atmosphere.data.local.AppDatabase
import ru.godsonpeya.atmosphere.data.local.dao.LanguageDao
import ru.godsonpeya.atmosphere.data.local.dao.SongBookDao
import ru.godsonpeya.atmosphere.data.local.dao.SongDao
import ru.godsonpeya.atmosphere.data.local.dao.VerseDao
import ru.godsonpeya.atmosphere.data.remote.network.ApiService
import ru.godsonpeya.atmosphere.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSongsDao(appDatabase: AppDatabase): SongDao = appDatabase.songDao()

    @Singleton
    @Provides
    fun provideSongBooksDao(appDatabase: AppDatabase): SongBookDao = appDatabase.songBookDao()

    @Singleton
    @Provides
    fun provideLanguageDao(appDatabase: AppDatabase): LanguageDao = appDatabase.languageDao()

    @Singleton
    @Provides
    fun provideVerseDao(appDatabase: AppDatabase): VerseDao = appDatabase.verseDao()

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "atmosphere_db")
            .fallbackToDestructiveMigration().build()

}