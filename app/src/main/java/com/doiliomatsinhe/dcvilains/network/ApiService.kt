package com.doiliomatsinhe.dcvilains.network

import com.doiliomatsinhe.dcvilains.model.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    //@GET("all")
    @GET("all.json")
    suspend fun getCharacters(): List<NetworkCharacter>

    @GET("id/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character?

}