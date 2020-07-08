package com.doiliomatsinhe.dcvilains.ui.villain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import java.lang.IllegalArgumentException

class VillainsViewModelFactory(private val repository: VillainRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VillainsViewModel::class.java)) {
            return VillainsViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Class Not Found")
    }


}