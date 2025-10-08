package com.ichin23.salbum.ui.screens.Profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.data.api.dto.salbum.listenlist.ListenListItem
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.domain.models.AlbumDetails
import com.ichin23.salbum.ui.components.RatingCardHome
import com.ichin23.salbum.ui.components.RatingCardHome
import com.ichin23.salbum.ui.theme.LightGreyText
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    authVM: AuthViewModel = hiltViewModel(),
    viewModel: ProfileScreenVM = hiltViewModel(),
    navController: NavController
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val ratings by viewModel.ratings.collectAsStateWithLifecycle()
    val refreshing by viewModel.refreshing.collectAsStateWithLifecycle()
    val listenList by viewModel.listenList.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        currentUser?.let { user ->
            PullToRefreshBox(
                isRefreshing = refreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(LightGreyText)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                user.name,
                                style = MaterialTheme.typography.displayMedium.copy(fontSize = 25.sp)
                            )
                            Text(
                                user.username,
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                                color = LightGreyText
                            )
                            Spacer(Modifier.width(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    user.followersCount.toString(),
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                                )
                                Text("Seguidores", color = LightGreyText)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    user.followingCount.toString(),
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                                )
                                Text("Seguindo", color = LightGreyText)
                            }
                            TextButton({ viewModel.onEvent(ProfileEvents.OnLogout) }) {
                                Text("Sair")
                            }
                        }
                    }

                    TabRow(selectedTabIndex = pagerState.currentPage) {
                        Tab(
                            selected = pagerState.currentPage == 0,
                            onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                            text = { Text("Avaliações") }
                        )
                        Tab(
                            selected = pagerState.currentPage == 1,
                            onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                            text = { Text("Listenlist") }
                        )
                    }

                    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                        when (page) {
                            0 -> {
                                if (ratings.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Não há reviews")
                                    }
                                } else {
                                    LazyColumn {
                                        items(ratings) { rating ->
                                            RatingCardHome(
                                                rating = rating,
                                                onClick = {
                                                    navController.navigate(ScreenName.DETAIL_ALBUM + "/${rating.album}")
                                                },
                                                imageUrl = null
                                            )
                                        }
                                    }
                                }
                            }
                            1 -> {
                                if (listenList.isEmpty()) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Não há álbuns na listenlist")
                                    }
                                } else {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(3),
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        items(listenList) { album ->
                                            ListenListAlbumCover(album = album) {
                                                navController.navigate(ScreenName.DETAIL_ALBUM + "/${album.item.id}")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum usuário encontrado")
            }
        }
    }
}

@Composable
fun ListenListAlbumCover(album: ListenListItem, onClick: () -> Unit) {
    AsyncImage(
        model = album.item.links.first { it.rel == "image" }.href,
        contentDescription = album.item.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    )
}
