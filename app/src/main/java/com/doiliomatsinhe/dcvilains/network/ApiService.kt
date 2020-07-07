package com.doiliomatsinhe.dcvilains.network

import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("all")
    suspend fun getVillains(): List<NetworkVillain>

    @GET("id/{id}")
    suspend fun getVillain(@Path("id") id: Int): Villain?

}

object VillainsApi {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}