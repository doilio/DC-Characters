package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.lifecycle.*
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.*

class VillainsViewModel(private val repository: VillainRepository) : ViewModel() {

//    val villainList: LiveData<List<Villain>> = liveData(Dispatchers.IO) {
//        val villainList = repository.getVillains()
//        emit(villainList.asDomainModel())
//    }

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val villains = repository.villains

    init {
        uiScope.launch {
            repository.refreshVillains()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}