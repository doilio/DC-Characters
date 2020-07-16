package com.doiliomatsinhe.dccharacters.ui.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doiliomatsinhe.dccharacters.repository.CharacterRepository
import java.lang.IllegalArgumentException

class CharacterDetailViewModelFactory(
    private val repository: CharacterRepository,
    private val villainId: Int
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailViewModel::class.java)) {
            return CharacterDetailViewModel(repository, villainId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}