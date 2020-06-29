package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.network.VillainsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class VillainsViewModel : ViewModel() {

    private val _villainsList = MutableLiveData<List<Villain>>()
    val villainsList: LiveData<List<Villain>>
        get() = _villainsList

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getListOfVillains()
    }

    private fun getListOfVillains() {
        uiScope.launch {
            try {
                _villainsList.value = VillainsApi.apiService.getVillains().body()
            } catch (ex: Exception) {
                Timber.d("Failed to get the list: ${ex.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}