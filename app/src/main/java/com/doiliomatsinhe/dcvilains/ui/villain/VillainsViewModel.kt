package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.*

class VillainsViewModel @ViewModelInject constructor
    (private val repository: VillainRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToVillainDetail = MutableLiveData<Villain>()
    val navigateToVillainDetail: LiveData<Villain>
        get() = _navigateToVillainDetail

    val villains = repository.villains

    init {
        uiScope.launch {
            repository.refreshVillains()
        }
    }

    fun onVillainClicked(villain: Villain) {
        _navigateToVillainDetail.value = villain
    }

    fun onVillainDetailNavigated() {
        _navigateToVillainDetail.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}