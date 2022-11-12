package com.godsonpeya.jetnavsampleapp.network

import com.godsonpeya.jetnavsampleapp.model.SongBooks
import com.godsonpeya.jetnavsampleapp.model.Songs
import com.godsonpeya.jetnavsampleapp.model.SongsItem
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/songbooks")
    suspend fun getSongBooks(): SongBooks

    @GET("/songs/songbook/{songbookId}")
    suspend fun getSongs(@Path("songbookId") songbookId: Int): Songs

    @GET("/songs/{songId}")
    suspend fun getSong(@Path("songId") songId: Int): SongsItem
}