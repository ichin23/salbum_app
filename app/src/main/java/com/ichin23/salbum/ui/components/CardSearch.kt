package com.ichin23.salbum.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ichin23.salbum.data.api.dto.musicbrainz.releaseGroup.ReleaseGroup
import com.ichin23.salbum.data.api.dto.salbum.musicmetadata.MusicMetadataCacheDTO
import com.ichin23.salbum.ui.theme.Inter

@Composable
fun CardSearch(item: MusicMetadataCacheDTO, modifier: Modifier = Modifier) {
    Row(
        modifier=modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model=item.links.find { link ->link.rel=="image" }?.href,
            contentDescription = "Cada de ${item.title}",
            modifier=Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(70.dp)
        )
        Spacer(Modifier.width(10.dp))
        Column(
            modifier = Modifier.height(70.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                item.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontFamily = Inter,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.3.sp
                ),
            )
//            Text(album.artists.joinToString(separator = ", ") { it -> it.name },
//                    style = MaterialTheme.typography.bodySmall,
//                color = LightGreyText)
        }
    }
}