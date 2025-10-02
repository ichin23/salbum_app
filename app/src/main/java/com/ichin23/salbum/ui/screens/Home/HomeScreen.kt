package com.ichin23.salbum.ui.screens.Home

import android.widget.Space
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.ui.components.AlbumHomeComponent
import com.ichin23.salbum.ui.components.LogoApp
import com.ichin23.salbum.ui.components.RatingCardHome

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: HomeScreenVM = hiltViewModel(),
    onClick: (id:String)->Unit
) {
    val albums by viewModel.albums.collectAsStateWithLifecycle()
    val ratings by viewModel.ratings.collectAsStateWithLifecycle()

    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    Column(
        Modifier
        .fillMaxSize()
        //.verticalScroll(rememberScrollState())
        .padding(vertical = 20.dp)
    ) {
        LogoApp()
        Spacer(Modifier.height(10.dp))
            PullToRefreshBox(
                isRefreshing = refreshing,
                onRefresh = {viewModel.refresh()},
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyColumn {


//                    item {
//                        Text(
//                            "Lançamentos Recentes",
//                            modifier = Modifier.padding(horizontal = 20.dp),
//                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
//                        )
//                    }
//                    item { Spacer(Modifier.height(15.dp)) }
//                    item {
//                        Row(
//                            modifier = Modifier
//                                .horizontalScroll(rememberScrollState())
//                                .padding(horizontal = 20.dp)
//                        ) {
//                            albums.forEachIndexed { index, item ->
//                                AlbumHomeComponent(animatedVisibilityScope, item, onClick)
//                                // Adicione um Spacer para criar um espaço entre os itens
//                                if (index < albums.lastIndex) {
//                                    Spacer(modifier = Modifier.width(16.dp))
//                                }
//                            }
//                        }
//                    }
                    item { Spacer(Modifier.height(10.dp)) }
                    item {
                        Text(
                            "Avaliações Recentes",
                            modifier = Modifier.padding(horizontal = 20.dp),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                        )
                    }
                    item { Spacer(Modifier.height(15.dp)) }
                    if (ratings!=null){
                        itemsIndexed(ratings!!.embedded.ratings) { index, item ->
                            RatingCardHome(item, onClick, null)
                            // Adicione um Spacer para criar um espaço entre os itens
                            if (index < ratings!!.embedded.ratings.lastIndex) {
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                    }
                }
            }
    }

}