package com.ichin23.salbum.ui.screens.Search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.R
import com.ichin23.salbum.ui.components.CardSearch
import com.ichin23.salbum.ui.theme.WhiteText
import java.util.logging.Level
import java.util.logging.Logger

@Composable
fun SearchScreen(focus:Boolean, onFocusHandled:() ->Unit, modifier: Modifier = Modifier, viewModel: SearchScreenVM = hiltViewModel()) {
    var searchState by rememberSaveable { mutableStateOf("") }
    var searchResult = viewModel.searchResult.collectAsStateWithLifecycle()
    var loading = viewModel.loading.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focus) {
        if(focus){
            Logger.getGlobal().log(Level.ALL, "Mete focus")
            focusRequester.requestFocus()
            onFocusHandled()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Column{
            TextField(
                value = searchState,
                onValueChange = {searchState=it},
                label = {Text("Busca")},
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.dp,
                        Color.Transparent,
                        RoundedCornerShape(12.dp)
                    ).focusRequester(focusRequester),
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
                colors = TextFieldDefaults.colors( focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)

            )

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
                    if (searchResult.value.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(searchResult.value) {
                                CardSearch(it)
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
                }
            }

        }
    }
}