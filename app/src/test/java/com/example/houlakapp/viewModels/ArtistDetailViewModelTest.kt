package com.example.houlakapp.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.houlakapp.data.SearchArtistRepository
import com.example.houlakapp.data.TokenRepository
import com.example.houlakapp.model.Artist
import com.example.houlakapp.model.Track
import com.example.houlakapp.model.TracksResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ArtistDetailViewModelTest {

    private val searchArtistsRepository: SearchArtistRepository = mock()
    private val tokenRepository: TokenRepository = mock()
    private lateinit var artistDetailViewModel: ArtistDetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onBefore() {
        artistDetailViewModel = ArtistDetailViewModel(searchArtistsRepository, tokenRepository)
    }


    @Test
    fun `when getArtistById returns success an artists is set in the state`() = runTest {
        //Given
        val artist = Artist(genres = listOf(), id = "id", images = listOf(), name = "gg", popularity = 90)
        val result = SearchArtistRepository.ArtistResult.Success(artist)
        val aValidId = "id"
        whenever(searchArtistsRepository.searchArtistById(aValidId)).thenReturn(flowOf(result))

        //When
        artistDetailViewModel.getArtistById(aValidId)

        //Then
        advanceUntilIdle()
        assert(artistDetailViewModel.state.value!!.artist?.id == result.data.id)
    }

    @Test
    fun `when getArtistById returns expired token then expired token state is set`() = runTest {
        //Given
        val result = SearchArtistRepository.ArtistResult.ExpiredToken
        val aValidId = "id"
        whenever(searchArtistsRepository.searchArtistById(aValidId)).thenReturn(flowOf(result))

        //When
        artistDetailViewModel.getArtistById(aValidId)

        //Then
        advanceUntilIdle()
        assert(artistDetailViewModel.state.value!!.expiredToken)
    }

    @Test
    fun `when getArtistById doesn't return expired token or success then generic error state is set`() = runTest {
        //Given
        val result = SearchArtistRepository.ArtistResult.NetworkError
        val aValidId = "id"
        whenever(searchArtistsRepository.searchArtistById(aValidId)).thenReturn(flowOf(result))

        //When
        artistDetailViewModel.getArtistById(aValidId)

        //Then
        advanceUntilIdle()
        assert(artistDetailViewModel.state.value!!.genericError)
    }

    @Test
    fun `when getTopTracks returns success a track list is set in the state`() = runTest {
        //Given
        val tracks: List<Track> = listOf()
        val tracksResponse = TracksResponse(tracks)
        val result = SearchArtistRepository.ArtistResult.Success(tracksResponse)
        val aValidId = "id"
        whenever(searchArtistsRepository.getTopTracks(aValidId)).thenReturn(flowOf(result))

        //When
        artistDetailViewModel.getTopTracks(aValidId)

        //Then
        advanceUntilIdle()
        assert(artistDetailViewModel.state.value!!.topTracks == result.data.tracks)
    }

    @Test
    fun `when getTopTracks returns expired token then expired token state is set`() = runTest {
        //Given
        val result = SearchArtistRepository.ArtistResult.ExpiredToken
        val aValidId = "id"
        whenever(searchArtistsRepository.getTopTracks(aValidId)).thenReturn(flowOf(result))

        //When
        artistDetailViewModel.getTopTracks(aValidId)

        //Then
        advanceUntilIdle()
        assert(artistDetailViewModel.state.value!!.expiredToken)
    }

    @Test
    fun `when getTopTracks doesn't return expired token or success then generic error state is set`() = runTest {
        //Given
        val result = SearchArtistRepository.ArtistResult.NetworkError
        val aValidId = "id"
        whenever(searchArtistsRepository.searchArtistById(aValidId)).thenReturn(flowOf(result))

        //When
        artistDetailViewModel.getArtistById(aValidId)

        //Then
        advanceUntilIdle()
        assert(artistDetailViewModel.state.value!!.genericError)
    }


}