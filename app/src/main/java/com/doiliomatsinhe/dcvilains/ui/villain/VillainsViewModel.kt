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


    fun getList(filters: Filters) {

        villains.addSource(repository.getVillains(filters), villains::setValue)

            //TODO Find a clean way to Implement sorting on this properties
        /* villains.addSource(
            when (filters.order) {
                "Sort by Combat Skill" -> repository.villainsCombat
                "Sort by Durability" -> repository.villainsDurability
                "Sort by Intelligence" -> repository.villainsIntelligence
                "Sort by Power" -> repository.villainsPower
                "Sort by Speed" -> repository.villainsSpeed
                "Sort by Strength" -> repository.villainsStrength
                else -> repository.villains
            }, villains::setValue
        )*/
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