package com.doiliomatsinhe.dccharacters.ui.character

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doiliomatsinhe.dccharacters.database.asDomainModel
import com.doiliomatsinhe.dccharacters.model.Filters
import com.doiliomatsinhe.dccharacters.model.Character
import com.doiliomatsinhe.dccharacters.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CharactersViewModel @ViewModelInject constructor
    (private val repository: CharacterRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToCharacterDetail = MutableLiveData<Character>()
    val navigateToCharacterDetail: LiveData<Character>
        get() = _navigateToCharacterDetail

    var filters: Filters = Filters()
    private val pagingConfig =
        PagingConfig(pageSize = 20, enablePlaceholders = false, maxSize = 300)

    fun getList(filters: Filters): Flow<PagingData<Character>> {
        return Pager(pagingConfig) {
            repository.getCharacters(filters)
        }.flow.map {
            it.asDomainModel()
        }
    }

    init {
        uiScope.launch {
            repository.refreshCharacters()
        }
        filters = Filters()
    }

    fun onCharacterClicked(character: Character) {
        _navigateToCharacterDetail.value = character
    }

    fun onCharacterDetailNavigated() {
        _navigateToCharacterDetail.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}