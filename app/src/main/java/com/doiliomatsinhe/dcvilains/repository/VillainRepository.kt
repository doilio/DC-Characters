package com.doiliomatsinhe.dcvilains.repository


import com.doiliomatsinhe.dcvilains.network.VillainsApi

class VillainRepository {
    private val villainsService = VillainsApi.apiService

    suspend fun getVillains() = villainsService.getVillains()

}