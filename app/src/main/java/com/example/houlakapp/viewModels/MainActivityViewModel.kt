package com.example.houlakapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.houlakapp.data.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel@Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

}