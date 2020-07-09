package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.*

class VillainsViewModel @ViewModelInject constructor
    (private val repository: VillainRepository) : ViewModel() {

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