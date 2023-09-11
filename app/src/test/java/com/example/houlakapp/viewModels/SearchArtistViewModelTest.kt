package com.example.houlakapp.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.houlakapp.data.SearchArtistRepository
import com.example.houlakapp.data.TokenRepository
import com.example.houlakapp.model.AccessTokenResponse
import com.example.houlakapp.model.ArtistResultsResponse
import com.example.houlakapp.model.ArtistsContainer
import com.example.houlakapp.model.BearerToken
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
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchArtistViewModelTest {

    private val searchArtistsRepository: SearchArtistRepository = mock()
    private val tokenRepository: TokenRepository = mock()
    private lateinit var searchArtistViewModel: SearchArtistViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun onBefore() {
        searchArtistViewModel = SearchArtistViewModel(searchArtistsRepository, tokenRepository)
    }

    @Test
    fun `when searchArtistRepository returns success a list of artists sets on the flow`() = runTest {
        //Given
        val artistsContainer = ArtistsContainer(href = "some", items = listOf(), total = 1, limit = 1, offset = 1, next = null, previous = null)
        val searchResponse  = ArtistResultsResponse(artistsContainer)
        val result = SearchArtistRepository.ArtistResult.Success(searchResponse)
        val aValidName = "aa"
        val aValidToken = BearerToken("1343", LocalDateTime.now(), 2000)
        whenever(searchArtistsRepository.searchArtistByName(aValidName, aValidToken)).thenReturn(flowOf(result))

        //When
        searchArtistViewModel.searchArtistByName(aValidName)

        //Then
        advanceUntilIdle()
        assert(searchArtistViewModel.searchArtistState.value?.artistList == result.data.artists.items)
    }

    @Test
    fun `when searchArtistRepository returns expired token then error state is set`() = runTest {
        //Given
        val result = SearchArtistRepository.ArtistResult.ExpiredToken
        val aValidName = "aa"
        val anExpiredToken = BearerToken("1343", LocalDateTime.now(), 0)
        whenever(searchArtistsRepository.searchArtistByName(aValidName, anExpiredToken)).thenReturn(flowOf(result))

        //When
        searchArtistViewModel.searchArtistByName(aValidName)

        //Then
        advanceUntilIdle()
        assert(searchArtistViewModel.searchArtistState.value!!.error)
    }

}