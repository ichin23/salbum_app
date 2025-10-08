package com.ichin23.salbum.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.R;
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingDTO
import com.ichin23.salbum.data.api.dto.salbum.rating.RatingUserDTO
import com.ichin23.salbum.ui.theme.WhiteText

@Composable
fun RatingCardHome(rating: RatingDTO, onClick: (id:String)->Unit, imageUrl: String?, modifier: Modifier = Modifier) {
    Row(
        modifier=modifier.fillMaxWidth().padding(12.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        if (rating.user.image_url==null){
            Icon(Icons.Default.Person, contentDescription = "Ícone pessoa", Modifier.size(40.dp))
        }else{
            AsyncImage(
                model=rating.user.image_url,
                contentDescription = "Foto de ${rating.user.username}",
                modifier=Modifier.clip(CircleShape)
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(Modifier.fillMaxWidth(0.6f)) {
            Row {
                Text("@${rating.user.username}", style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp))
            }
            Spacer(Modifier.height(8.dp))
            Text("Avaliou com ${rating.rate} estrelas esse álbum!", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(12.dp))
            Row{
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.like_outlined),
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(WhiteText),
                    contentDescription = "Botão like",

                )
                Spacer(Modifier.width(8.dp))
                Text("587")
                Spacer(Modifier.width(25.dp))
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.comment_outlined),
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(WhiteText),
                    contentDescription = "Botão like"

                )
                Spacer(Modifier.width(8.dp))
                Text("123")
            }
        }
        Spacer(Modifier.width(8.dp))
        AsyncImage(
            model=imageUrl ?: rating.links.image.href,
            contentDescription = "Capa de ${rating.album}",
            modifier=Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)).clickable{
                    onClick(rating.album)
                }
        )
    }
}

@Composable
fun RatingCardHome(rating: RatingUserDTO, onClick: (id:String)->Unit, imageUrl: String?, modifier: Modifier = Modifier) {
    Row(
        modifier=modifier.fillMaxWidth().padding(12.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        if (rating.user.image_url==null){
            Icon(Icons.Default.Person, contentDescription = "Ícone pessoa", Modifier.size(40.dp))
        }else{
            AsyncImage(
                model=rating.user.image_url,
                contentDescription = "Foto de ${rating.user.username}",
                modifier=Modifier.clip(CircleShape)
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(Modifier.fillMaxWidth(0.6f)) {
            Row {
                Text("@${rating.user.username}", style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp))
            }
            Spacer(Modifier.height(8.dp))
            Text("Avaliou com ${rating.rate} estrelas esse álbum!", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(12.dp))
            Row{
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.like_outlined),
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(WhiteText),
                    contentDescription = "Botão like",

                    )
                Spacer(Modifier.width(8.dp))
                Text("587")
                Spacer(Modifier.width(25.dp))
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.comment_outlined),
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(WhiteText),
                    contentDescription = "Botão like"

                )
                Spacer(Modifier.width(8.dp))
                Text("123")
            }
        }
        Spacer(Modifier.width(8.dp))
        AsyncImage(
            model=imageUrl ?: rating.links.first { it.rel=="image" }.href,
            contentDescription = "Capa de ${rating.album}",
            modifier=Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)).clickable{
                    onClick(rating.album)
                }
        )
    }
}