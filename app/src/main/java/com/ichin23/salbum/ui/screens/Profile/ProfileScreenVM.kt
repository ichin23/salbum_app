package com.ichin23.salbum.ui.screens.Profile

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.core.datastore.UserStateOuterClass
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.domain.models.User
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenVM @Inject constructor(
    val userRepository: UserRepository,
    val ratingsRepository: RatingsRepository,
    val userStateDataStore: DataStore<UserStateOuterClass.UserState>
): ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _ratings = MutableStateFlow<List<Ratings>>(arrayListOf())
    val ratings = _ratings.asStateFlow()

    private val _refreshing = MutableStateFlow<Boolean>(false)
    val refreshing = _refreshing.asStateFlow()

    init{
        _currentUser.value = userRepository.getCurrentUser()
        _ratings.value=ratingsRepository.getRatingsByUser(currentUser.value!!.id)
    }

    fun refresh(){
        viewModelScope.launch {
            _ratings.value=ratingsRepository.getRatingsByUser(currentUser.value!!.id)
        }
    }
}