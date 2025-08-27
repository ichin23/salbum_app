package com.ichin23.salbum.ui.components

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ichin23.salbum.R
import com.ichin23.salbum.ui.theme.BluePrimary

@Composable
fun LogoApp(
    color: Color = MaterialTheme.colorScheme.primary,
    imageHeight: Dp = 40.dp,
    fontSize: TextUnit = 30.sp,
    modifier: Modifier = Modifier
) {
    val logo: ImageVector = ImageVector.vectorResource(R.drawable.phone_logo)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,

    ) {
        Image(
            modifier= Modifier.height(imageHeight),
            imageVector = logo,
            contentDescription = "Salbum Logo",
            colorFilter=ColorFilter.tint(color),
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text="Salbum",
            style = MaterialTheme.typography.titleLarge.copy(fontSize=fontSize),
            color = color
        )
    }
}


