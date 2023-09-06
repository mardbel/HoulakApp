package com.example.houlakapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houlakapp.data.SearchArtistRepositoryImp
import com.example.houlakapp.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val searchArtistRepository: SearchArtistRepositoryImp) : ViewModel() {

    private var _artistList = MutableLiveData<List<Artist>>()
    val artistList: LiveData<List<Artist>>
        get() = _artistList

    private val _welcomeState = MutableLiveData<Boolean>().apply {  postValue(true) }
    val welcomeState: LiveData<Boolean> get() = _welcomeState

    fun searchArtistByName(name: String) {
        _welcomeState.value = false
        viewModelScope.launch {
            val response = searchArtistRepository.searchArtistByName(name)
            _artistList.value = response.body()?.artists?.items

        }
    }
}