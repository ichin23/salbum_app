package com.ichin23.salbum.ui.screens.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.ui.components.RatingCardHome
import com.ichin23.salbum.ui.theme.LightGreyText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, authVM: AuthViewModel = hiltViewModel(), viewModel: ProfileScreenVM = hiltViewModel()) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val ratings by viewModel.ratings.collectAsStateWithLifecycle()
    val refreshin by viewModel.refreshing.collectAsStateWithLifecycle()
    val user by authVM.userState.collectAsStateWithLifecycle()

    Box {
        if(currentUser==null){
            Text("Nenhum usuário encontrado")
        }else{
            PullToRefreshBox(
                isRefreshing = refreshin,
                onRefresh = { viewModel.refresh() },
                Modifier.fillMaxWidth()

            ) {
                LazyColumn {
                    item {
                        Spacer(Modifier.width(30.dp))
                    }
                    item {
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
                                    user.user.name,
                                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 25.sp)
                                )
                                Text(
                                    user.user.username,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                                    color = LightGreyText
                                )
                                Spacer(Modifier.width(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        currentUser!!.followersCount.toString(),
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                                    )
                                    Text("Seguidores", color = LightGreyText)
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        currentUser!!.followingCount.toString(),
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                                    )
                                    Text("Seguindo", color = LightGreyText)
                                }
                                TextButton({viewModel.onEvent(ProfileEvents.OnLogout)}) {
                                    Text("Sair")
                                }
                            }
                        }
                    }
                    item {
                        Text(
                            "Avaliações Recentes",
                            modifier = Modifier.padding(horizontal = 20.dp),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                        )
                    }
                    itemsIndexed(ratings) { index, rating ->
                        //RatingCardHome(rating)
                        // Adicione um Spacer para criar um espaço entre os itens
                        if (index < ratings.lastIndex) {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }
        }
    }
}