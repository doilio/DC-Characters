package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.Dispatchers

class VillainsViewModel : ViewModel() {

    private val repository = VillainRepository()

    val villainList: LiveData<List<Villain>> = liveData(Dispatchers.IO) {
        val villainList = repository.getVillains()
        emit(villainList)
    }

}