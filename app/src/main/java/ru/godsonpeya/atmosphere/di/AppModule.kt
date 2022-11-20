package ru.godsonpeya.atmosphere.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.godsonpeya.atmosphere.BuildConfig
import ru.godsonpeya.atmosphere.data.local.AppDatabase
import ru.godsonpeya.atmosphere.data.local.dao.LanguageDao
import ru.godsonpeya.atmosphere.data.local.dao.SongBookDao
import ru.godsonpeya.atmosphere.data.local.dao.SongDao
import ru.godsonpeya.atmosphere.data.local.dao.VerseDao
import ru.godsonpeya.atmosphere.data.remote.network.ApiService
import ru.godsonpeya.atmosphere.utils.Constants
import java.util.concurrent.TimeUnit
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
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideBasicOkHttpClient().build()).build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "atmosphere_db")
            .fallbackToDestructiveMigration().build()

}

private fun provideBasicOkHttpClient() =
    OkHttpClient.Builder().readTimeout(40, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(240, TimeUnit.SECONDS).apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }