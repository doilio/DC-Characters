package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.dcvilains.model.Filters
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class VillainsViewModel @ViewModelInject constructor
    (private val repository: VillainRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToVillainDetail = MutableLiveData<Villain>()
    val navigateToVillainDetail: LiveData<Villain>
        get() = _navigateToVillainDetail

    var filters: Filters = Filters()

    val villains = MediatorLiveData<List<Villain>>()
    //val villains = repository.villains


    fun getList(filters: Filters) {
        villains.addSource(repository.getVillains(filters), villains::setValue)
    }

    init {
        uiScope.launch {
            repository.refreshVillains()
        }
        filters = Filters()
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