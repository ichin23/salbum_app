package com.ichin23.salbum.ui.screens.FullSearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.categoriesList
import com.ichin23.salbum.domain.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FullSearchVM @Inject constructor(var salbumService: ApiSalbumService): ViewModel() {

    private var _searchCategory = MutableStateFlow(categoriesList.first())
    var searchCategory = _searchCategory.asStateFlow()

    private var _searchData = MutableStateFlow<List<Any>>(listOf())
    var searchData = _searchData.asStateFlow()

    private var _loading = MutableStateFlow(false)
    var loading = _loading.asStateFlow()

    fun changeCategory(newCategory: Category){
        _searchCategory.value=newCategory;
    }

    fun search(query: String){
        viewModelScope.launch {
            _loading.value=true
            try {
                if(_searchCategory.value.value=="releaseGroup"){
                    _searchData.value = salbumService.searchAlbum(query).releaseGroups
                }
            }catch (e: HttpException){
                Log.e("Req error", e.response()?.raw()?.request?.headers.toString())
                Log.e("Req error", e.response()?.raw()?.request?.url.toString())
                Log.e("Req error", e.code().toString())
                Log.e("Req error", e.response()?.body().toString())
            }

            _loading.value = false
        }
    }
}