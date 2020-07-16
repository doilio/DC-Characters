package com.doiliomatsinhe.dcvilains.ui.characterdetail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.doiliomatsinhe.dcvilains.model.Character
import com.doiliomatsinhe.dcvilains.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val repository: CharacterRepository,
    private val characterId: Int
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val character = MediatorLiveData<Character>()

    fun getVillain() = character

    init {
        uiScope.launch {
            character.addSource(repository.getCharacterById(characterId), character::setValue)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}