package com.example.houlakapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houlakapp.data.SearchArtistRepository
import com.example.houlakapp.data.TokenRepository
import com.example.houlakapp.model.Artist
import com.example.houlakapp.model.BearerToken
import com.example.houlakapp.model.Track
import com.example.houlakapp.util.SharePreferencesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel@Inject constructor(
    private val searchArtistRepository: SearchArtistRepository,
    private val tokenRepository: TokenRepository,
    private val sharePreferencesProvider: SharePreferencesProvider,
) : ViewModel() {

    private val mState = MutableLiveData<ArtistDetailState>()
    val state: LiveData<ArtistDetailState>
        get() = mState

    init {
        viewModelScope.launch {
            setInitialState()
            getToken()
        }
    }

    private var mToken = MutableLiveData<BearerToken>()

    fun getArtistById(id: String) {
        viewModelScope.launch {
                searchArtistRepository.searchArtistById(id, sharePreferencesProvider.getSavedToken()).collect {
                    when (it) {
                        is SearchArtistRepository.ArtistResult.Success -> mState.value = mState.value?.copy(artist = it.data)
                        is SearchArtistRepository.ArtistResult.ExpiredToken -> {
                            getToken()
                            mState.value  = mState.value?.copy(expiredToken = true)
                        }
                        else -> mState.value = mState.value?.copy(genericError = true)
                    }
                }

        }
    }

    fun getTopTracks(id: String) {
        viewModelScope.launch {
            searchArtistRepository.getTopTracks(id, sharePreferencesProvider.getSavedToken()).collect {
                when (it) {
                    is SearchArtistRepository.ArtistResult.Success -> {
                        var responseSize = 0
                        responseSize = if(it.data!!.tracks.size > 4) 5
                        else it.data.tracks.size

                        mState.value = mState.value?.copy(topTracks = it.data.tracks.sortedByDescending { it.popularity }.subList(0, responseSize))
                    }
                    is SearchArtistRepository.ArtistResult.ExpiredToken -> {
                        getToken()
                        mState.value  = mState.value?.copy(expiredToken = true)
                    }
                    else -> mState.value = mState.value?.copy(genericError = true)
                }
            }
        }
    }

    private suspend fun getToken() {
        when (val response = tokenRepository.getToken()) {
            is TokenRepository.AuthenticationResult.Success -> mToken.value = BearerToken(
                response.token.accessToken,
                LocalDateTime.now(),
                response.token.secondsUntilExpiration
            )
            else -> null
        }
    }

    private fun setInitialState() {
        mState.value = ArtistDetailState(
            artist = null,
            topTracks = mutableListOf(),
            genericError = false,
            expiredToken = false
        )
    }
}

data class ArtistDetailState(
    val artist: Artist?,
    val topTracks: List<Track>,
    val genericError: Boolean,
    val expiredToken: Boolean
)

