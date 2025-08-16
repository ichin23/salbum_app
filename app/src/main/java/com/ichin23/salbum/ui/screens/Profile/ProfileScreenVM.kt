package com.ichin23.salbum.ui.screens.Profile

import androidx.lifecycle.ViewModel
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.domain.models.User
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileScreenVM @Inject constructor(userRepository: UserRepository, ratingsRepository: RatingsRepository): ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _ratings = MutableStateFlow<List<Ratings>>(arrayListOf())
    val ratings = _ratings.asStateFlow()

    init{
        _currentUser.value = userRepository.getCurrentUser()
        _ratings.value=ratingsRepository.getRatingsByUser(currentUser.value!!.id)
    }

}