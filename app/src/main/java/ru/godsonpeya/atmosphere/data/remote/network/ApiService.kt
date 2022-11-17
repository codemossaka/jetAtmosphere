package ru.godsonpeya.atmosphere.data.remote.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.godsonpeya.atmosphere.data.remote.dto.*


interface ApiService {

    @GET("/songbooks")
    suspend fun getSongBooks(): SongBooks

    @GET("/songbooks/by-language")
    suspend fun getSongBooksByLanguages(): CustomSongBooks

    @GET("/songs/songbook/{songbookId}")
    suspend fun getSongs(@Path("songbookId") songbookId: Int): Songs

    @GET("/songs/{songId}")
    suspend fun getSong(@Path("songId") songId: Int): SongsItem

    @GET("/songbooks/{songbookId}")
    fun getSongBook(@Path("songbookId") songbookId: Int): Call<SongBookDto>
}