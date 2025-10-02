package com.ichin23.salbum.ui.screens.Musics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ichin23.salbum.data.api.dto.musicbrainz.release.Track
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.screens.AlbumDetails.AlbumDetailsVM

@Composable
fun MusicsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val albumFlowEntry = remember(navController.currentBackStackEntry){
        navController.getBackStackEntry(ScreenName.ALBUM_FLOW)
    }

    val viewModel: AlbumDetailsVM = hiltViewModel(albumFlowEntry)

    val album = viewModel.albumTest.collectAsStateWithLifecycle()
    LazyColumn {
        item{
            Icon(Icons.Default.ArrowBack, "", modifier = Modifier.clickable { navController.popBackStack() })
        }
        item {
            Text(text = "Musicas")
        }
        items (album.value!!.release.media.first().tracks){
            Text("${it.position} - ${it.title}")
        }
    }
}