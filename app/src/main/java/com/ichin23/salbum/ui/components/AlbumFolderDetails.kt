package com.ichin23.salbum.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.ichin23.salbum.R
import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.ui.theme.WhiteText
import com.ichin23.salbum.ui.theme.YellowTertiary

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AlbumFolderDetails(albumDetail: ReleaseDTO, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier

            .graphicsLayer (
                shadowElevation = 8f,
                shape = RoundedCornerShape(
                    bottomEnd = 45.dp,
                    bottomStart = 45.dp
                )).fillMaxWidth().aspectRatio(1f)
    ) {
        val (image, gradient, title, nota) = createRefs()
        AsyncImage(
            model = albumDetail.image,
            contentDescription = "Capa",
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 45.dp, bottomStart = 45.dp))
                .fillMaxWidth()
                .aspectRatio(1f)

                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom

                    )
                    height= Dimension.fillToConstraints
                },


        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 45.dp, bottomStart = 45.dp)).fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x00000000),
                            Color(0xB3020202)
                        )
                    )
                ).fillMaxWidth().aspectRatio(1f)

                .constrainAs(gradient) {
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        top = parent.top,
                        bottom = parent.bottom
                    )
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = albumDetail.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .constrainAs(title) {
                bottom.linkTo(parent.bottom, margin = 20.dp)
                start.linkTo(parent.start, margin = 20.dp)
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.constrainAs (nota){
                bottom.linkTo(parent.bottom, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
            }
        ){
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.estrela_fill),
                contentDescription = "Estrela",
                colorFilter = ColorFilter.tint(YellowTertiary),
                modifier = Modifier.size(30.dp)
            )
            Text("4.3", style = MaterialTheme.typography.displayMedium, color = WhiteText)
        }

    }
}