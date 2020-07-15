package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doiliomatsinhe.dcvilains.database.asDomainModel
import com.doiliomatsinhe.dcvilains.model.Filters
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VillainsViewModel @ViewModelInject constructor
    (private val repository: VillainRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToVillainDetail = MutableLiveData<Villain>()
    val navigateToVillainDetail: LiveData<Villain>
        get() = _navigateToVillainDetail

    var filters: Filters = Filters()
    private val pagingConfig =
        PagingConfig(pageSize = 20, enablePlaceholders = false, maxSize = 300)

    fun getList(filters: Filters): Flow<PagingData<Villain>> {
        return Pager(pagingConfig) {
            repository.getVillains(filters)
        }.flow.map {
            it.asDomainModel()
        }
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