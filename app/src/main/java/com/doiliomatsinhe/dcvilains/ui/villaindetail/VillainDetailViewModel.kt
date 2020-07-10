package com.doiliomatsinhe.dcvilains.ui.villaindetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VillainDetailViewModel(
    private val repository: VillainRepository,
    private val villainId: Int
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val villain = MediatorLiveData<Villain>()

    fun getVillain() = villain

    init {
        uiScope.launch {
            villain.addSource(repository.getVillainById(villainId), villain::setValue)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}