package com.doiliomatsinhe.dcvilains.ui.villaindetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import java.lang.IllegalArgumentException

class VillainDetailViewModelFactory(
    private val repository: VillainRepository,
    private val villainId: Int
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VillainDetailViewModel::class.java)) {
            return VillainDetailViewModel(repository, villainId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}