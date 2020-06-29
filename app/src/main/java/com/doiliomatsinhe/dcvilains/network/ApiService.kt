package com.doiliomatsinhe.dcvilains.network

import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.utils.BASE_URL
import retrofit2.Call
import retrofit2.Response
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
    suspend fun getVillains(): Response<List<Villain>>

    @GET("id/{id}")
    fun getVillain(@Path("id") id: Int): Call<Villain?>

}

object VillainsApi {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}