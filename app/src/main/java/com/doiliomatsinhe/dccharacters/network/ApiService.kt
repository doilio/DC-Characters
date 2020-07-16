package com.doiliomatsinhe.dccharacters.network

import retrofit2.http.GET

interface ApiService {

    @GET("all.json")
    suspend fun getCharacters(): List<NetworkCharacter>

}