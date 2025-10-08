package com.ichin23.salbum.ui.screens.FullSearch

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.R
import com.ichin23.salbum.data.api.dto.musicbrainz.releaseGroup.ReleaseGroup
import com.ichin23.salbum.data.categoriesList
import com.ichin23.salbum.ui.components.CardSearch
import com.ichin23.salbum.ui.components.SearchCategory
import com.ichin23.salbum.ui.theme.WhiteText

@Composable
fun FullSearchScreen(onNavigate: (idAlbum:String)->Unit, viewModel:FullSearchVM = hiltViewModel(), modifier: Modifier = Modifier) {

    var searchState by rememberSaveable { mutableStateOf("") }
    var searchCategory = viewModel.searchCategory.collectAsStateWithLifecycle()
    var searchData = viewModel.searchData.collectAsStateWithLifecycle()
    var loading = viewModel.loading.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Column {
            TextField(
                value = searchState,
                onValueChange = { searchState = it },
                label = { Text("Busca") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.dp,
                        Color.Transparent,
                        RoundedCornerShape(12.dp)
                    ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.search(searchState)
                    }
                ),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )

            )
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                items(categoriesList){
                    SearchCategory(it, isSelected = searchCategory.value.value==it.value, onClick = {viewModel.changeCategory(it)})
                    Spacer(Modifier.width(3.dp))
                }
            }

            if (searchState.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.lupa_outlined),
                        contentDescription = "Icone de busca",
                        colorFilter = ColorFilter.tint(WhiteText)
                    )
                    Text("Digite algo para pesquisa")
                }
            }else{
                if(loading.value){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }else{
            if (searchData.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    items(searchData.value) {
                        if(searchCategory.value.value=="releaseGroup"){
                            CardSearch(it as ReleaseGroup, modifier = Modifier.clickable{
                                Log.i("Click", it.id)
                                onNavigate(it.releases.first { r -> r.title== it.title }.id)
                            })
                        }
                    }
                }
            }else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.lupa_outlined),
                        contentDescription = "Icone de busca",
                        colorFilter = ColorFilter.tint(WhiteText)
                    )
                    Text("Nada foi encontrado...")
                }
            }
        }}}
    }
}

@Preview
@Composable
fun FullSearchScreenPreview() {
    FullSearchScreen({})
}