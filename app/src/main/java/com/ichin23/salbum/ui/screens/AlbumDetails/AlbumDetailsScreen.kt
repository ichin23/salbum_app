package com.ichin23.salbum.ui.screens.AlbumDetails

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.Space
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.ui.theme.GreyText
import com.ichin23.salbum.ui.components.AlbumFolderDetails
import com.ichin23.salbum.ui.components.AlbumInfoTag
import com.ichin23.salbum.ui.components.ListenExternal
import com.ichin23.salbum.ui.components.RatingCardHome
import com.ichin23.salbum.ui.theme.BackgroundLighterDark
import com.ichin23.salbum.ui.theme.BluePrimary
import com.ichin23.salbum.ui.theme.LightGreyText
import com.ichin23.salbum.ui.theme.WhiteText
import com.ichin23.salbum.R
import com.ichin23.salbum.domain.models.ExternalUrls
import com.ichin23.salbum.ui.components.FiveStarRating
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.AlbumDetailsScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    albumId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailsVM = hiltViewModel()
) {
    //val albumDetail by viewModel.albumDetails.collectAsStateWithLifecycle()
    val albumTest by viewModel.albumTest.collectAsStateWithLifecycle()
    val ratings by viewModel.reviews.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var ratingValue by remember { mutableDoubleStateOf(0.toDouble()) }

    albumTest?.let { it ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                //.padding(it)
                .padding(top = 0.dp)
        ) {
            val (content, backButton, floatingButton) = createRefs()
            LazyColumn(

                modifier = modifier
                    .constrainAs(content) {
                        linkTo(
                            top = parent.top,
                            start = parent.start,
                            bottom = parent.bottom,
                            end = parent.end
                        )
                    }
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    AlbumFolderDetails(
                        albumTest!!, modifier = Modifier
                            .fillMaxWidth()
                            .sharedElement(
                                rememberSharedContentState(key = "album-${albumTest!!.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )
                }

                items(albumTest!!.artistCredit) { artist ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(45.dp)
                                    .background(WhiteText)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(artist.name)
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier.border(
                                width = 2.dp,
                                color = WhiteText,
                                shape = RoundedCornerShape(40.dp)
                            ),
                            colors = ButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = WhiteText,
                                disabledContentColor = LightGreyText,
                                disabledContainerColor = GreyText
                            )
                        ) {
                            Text(
                                "Seguir",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                color = WhiteText
                            )
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .border(
                                    width = 3.dp,
                                    shape = RoundedCornerShape(35.dp),
                                    color = BluePrimary
                                )
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(35.dp))
                                .background(BackgroundLighterDark)
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "Álbum",
                                style = MaterialTheme.typography.titleLarge.copy(color = BluePrimary)
                            )
                            Spacer(Modifier.height(8.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AlbumInfoTag("Lançamento", albumTest!!.date)
                                AlbumInfoTag("Status", albumTest!!.status)
                                AlbumInfoTag("País", albumTest!!.country)
                                AlbumInfoTag("MBID", albumTest!!.id)
                            }
                        }
                    }
                }
                item{
                    Text("Músicas", modifier = Modifier.clickable{

                    })
                }
                item {
                    Column {
                        if(albumTest!!.relations.isNotEmpty()){
                            ListenExternal(ExternalUrls(albumTest!!.relations.first().url.toString()) )
                        }
                        Text(
                            "Reviews",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
                        )
                    }
                }

                itemsIndexed(items = ratings) { index, item ->
                    RatingCardHome(item)
                    // Adicione um Spacer para criar um espaço entre os itens
                    if (index < ratings.lastIndex) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .constrainAs(backButton) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                    }
                    .background(GreyText.copy(alpha = 0.35f))

            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Botão voltar",
                    modifier = Modifier
                        .clickable {
                            onBack()
                        }
                        .padding(12.dp)
                        .size(30.dp)
                )
            }

            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
                    .constrainAs (floatingButton){
                        bottom.linkTo(parent.bottom, margin = 20.dp)
                        end.linkTo(parent.end, margin = 20.dp)
                    }
                    .clickable{showBottomSheet=true}
                    .background(BluePrimary)
            ){
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.add),
                    contentDescription = "Add Review",
                    colorFilter = ColorFilter.tint(LightGreyText),
                    modifier = Modifier.padding(20.dp).size(22.dp)
                )
            }
        }
    }

    if(showBottomSheet){
        val loadingSend by viewModel.loadingSendRating.collectAsStateWithLifecycle()
        ModalBottomSheet(
            onDismissRequest = {showBottomSheet=false},
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp),

            ) {
                Text("Sua avaliação para ${albumTest!!.title}", style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp))
                Spacer(Modifier.height(8.dp))
                FiveStarRating(ratingValue, {ratingValue=it}, modifier = Modifier.align(Alignment.CenterHorizontally), starSize = 40.dp)
                Spacer(Modifier.height(8.dp))
                Button({
                    viewModel.onEvent(AlbumDetailsEvent.SendReviewAlbum(ratingValue))
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if(!sheetState.isVisible){
                            showBottomSheet=false
                        }
                    }
                },
                    modifier=Modifier.align(Alignment.End),
                    enabled = !loadingSend
                ) {
                    if(loadingSend){
                        CircularProgressIndicator()
                    }else{
                        Text("Avaliar")
                    }
                }
            }
        }
    }

}