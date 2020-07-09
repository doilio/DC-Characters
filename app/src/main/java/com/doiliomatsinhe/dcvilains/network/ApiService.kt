package com.doiliomatsinhe.dcvilains.network

import com.doiliomatsinhe.dcvilains.model.Villain
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("all")
    suspend fun getVillains(): List<NetworkVillain>

    @GET("id/{id}")
    suspend fun getVillain(@Path("id") id: Int): Villain?

}