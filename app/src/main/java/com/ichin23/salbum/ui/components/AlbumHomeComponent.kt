package com.ichin23.salbum.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.ui.theme.LightGreyText
import com.ichin23.salbum.ui.theme.WhiteText

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AlbumHomeComponent(
                       animatedVisibilityScope: AnimatedVisibilityScope,album: Album, navigateDetails: (id:String)->Unit, modifier: Modifier = Modifier) {


    ConstraintLayout(
        modifier = modifier
            .size(180.dp)
            .sharedElement(
                rememberSharedContentState(key = "album-${album.id}"),
                animatedVisibilityScope = animatedVisibilityScope
            ).clickable{
            navigateDetails(album.id)
        }
    ) {
        val (backgroundImage, gradient, name, artists) = createRefs()

        AsyncImage(
            model=album.imagesUrl,
            contentDescription = "Cada de ${album.name}",
            modifier=Modifier
                .clip(RoundedCornerShape(12.dp))

                .constrainAs(backgroundImage){
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                    height= Dimension.fillToConstraints
                }
        )
        Box(
            modifier=Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(brush = Brush.verticalGradient(
                    colors = listOf(Color(0x00000000),
                    Color(0xB3020202))
                ))
                .constrainAs(gradient){
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = parent.bottom
                )
                height= Dimension.fillToConstraints
                width= Dimension.fillToConstraints
            }
        ){}
        Text(
            text = album.artists.map { it-> it.name }.joinToString(separator = ", "),
            modifier = Modifier
                .padding(horizontal = 8.dp)

                .constrainAs(artists){
                bottom.linkTo(parent.bottom, margin = 4.dp)
            },
            style = MaterialTheme.typography.bodySmall,
            color = LightGreyText
        )
        Text(
            text = album.name,
            modifier = Modifier.padding(horizontal = 8.dp).constrainAs(name) {
                bottom.linkTo(
                    anchor = artists.top, margin = 2.dp
                )
            },
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
            color = WhiteText
        )
    }
}