package com.ichin23.salbum.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.ichin23.salbum.R
import com.ichin23.salbum.domain.models.ExternalUrls
import androidx.core.net.toUri

@Composable
fun ListenExternal(external: ExternalUrls, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 12.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Ouvir",
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 25.sp)
        )
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.spotify_oulined),
            contentDescription = "Spotify Logo",
            modifier = Modifier.size(40.dp).clickable{
                val intent = Intent(Intent.ACTION_VIEW, external.spotify.toUri())
                context.startActivity(intent)
            }
        )

    }
}