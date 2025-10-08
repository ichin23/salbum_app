package com.ichin23.salbum.ui.screens.Profile

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.core.datastore.UserStateOuterClass
import com.ichin23.salbum.domain.models.User
import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItem
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingUserDTO
import com.ichin23.salbum.domain.repository.AlbumRepository
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
    val albumRepository: AlbumRepository,
    val userStateDataStore: DataStore<UserStateOuterClass.UserState>
): ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _ratings = MutableStateFlow<List<RatingUserDTO>>(arrayListOf())
    val ratings = _ratings.asStateFlow()

    private val _listenList = MutableStateFlow<List<ListenListItem>>(emptyList())
    val listenList = _listenList.asStateFlow()

    private val _refreshing = MutableStateFlow<Boolean>(false)
    val refreshing = _refreshing.asStateFlow()

    init{
        viewModelScope.launch {
            _currentUser.value = userRepository.getCurrentUser()
            _currentUser.value?.let {
                _ratings.value = ratingsRepository.getRatingsByUser(it.id)
            }
            loadListenList()
        }
    }

    fun onEvent(event: ProfileEvents){
        when(event){
            ProfileEvents.OnLogout -> logout()
        }
    }

    private fun logout(){
        viewModelScope.launch {
            userStateDataStore.updateData {
                UserStateOuterClass.UserState.getDefaultInstance()
            }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            _currentUser.value?.let {
                _ratings.value = ratingsRepository.getRatingsByUser(it.id)
            }
            loadListenList()
        }
    }

    private fun loadListenList(){
        viewModelScope.launch {
            _listenList.value = albumRepository.getListenList()
        }
    }
}