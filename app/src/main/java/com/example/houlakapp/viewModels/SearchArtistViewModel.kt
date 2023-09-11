package com.example.houlakapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houlakapp.data.SearchArtistRepository
import com.example.houlakapp.data.TokenRepository
import com.example.houlakapp.di.SpotifyBaseUrls
import com.example.houlakapp.model.Artist
import com.example.houlakapp.model.BearerToken
import com.example.houlakapp.util.SharePreferencesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(
    private val searchArtistRepository: SearchArtistRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val mSearchArtistState = MutableLiveData<SearchArtistState>()
    val searchArtistState: LiveData<SearchArtistState>
        get() = mSearchArtistState

    init {
        viewModelScope.launch {
            setInitialState()
            getToken()
        }
    }

    private var mToken = MutableLiveData<BearerToken>()

    fun searchArtistByName(name: String) {
        mSearchArtistState.value = mSearchArtistState.value?.copy(isLoading = true)
        viewModelScope.launch {
            if (mToken.value != null) {
                getArtist(name)
            } else {
                getToken()
            }
        }
    }

    fun newSearch() {
        mSearchArtistState.value = mSearchArtistState.value?.copy(showSearcher = true)
    }

    private suspend fun getArtist(name: String) {
        if (mToken.value != null) {}
        searchArtistRepository.searchArtistByName(name, mToken.value!!).collect { artistResult ->
            when (artistResult) {
                is SearchArtistRepository.ArtistResult.Success ->
                    if (!artistResult.data?.artists?.items.isNullOrEmpty()) {
                        mSearchArtistState.value = mSearchArtistState.value?.copy(
                        artistList = artistResult.data!!.artists.items.sortedByDescending { it.popularity },
                        isLoading = false,
                        showSearcher = false
                    )
                    } else {
                        mSearchArtistState.value = mSearchArtistState.value?.copy(
                            error = true
                        )
                    }

                is SearchArtistRepository.ArtistResult.ExpiredToken -> getToken()
                else -> mSearchArtistState.value = mSearchArtistState.value?.copy(
                    isLoading = false,
                    showSearcher = true,
                    error = true
                )
            }
        }
    }

    private suspend fun getToken() {
        mSearchArtistState.value = mSearchArtistState.value?.copy(isLoading = true)
        when (val response = tokenRepository.getToken()) {
            is TokenRepository.AuthenticationResult.Success ->
            { mToken.value = BearerToken(
                response.token.accessToken,
                LocalDateTime.now(),
                response.token.secondsUntilExpiration
            )
            }
            else -> null
        }
    }

    private fun setInitialState() {
        mSearchArtistState.value = SearchArtistState(
            artistList = mutableListOf(),
            isLoading = false,
            error = false,
            showSearcher = true
        )
    }

}

data class SearchArtistState(
    val artistList: List<Artist>,
    val isLoading: Boolean,
    val error: Boolean,
    val showSearcher: Boolean
)