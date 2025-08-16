package com.ichin23.salbum.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ichin23.salbum.ui.theme.Jersey25
import com.ichin23.salbum.ui.theme.LightGreyText
import com.ichin23.salbum.ui.theme.WhiteText

@Composable
fun AlbumInfoTag(title:String, content:String, modifier: Modifier = Modifier) {
    Row {
        Text(
            text="${title}: ",
            style = TextStyle(
                fontFamily = Jersey25,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = WhiteText
            )
        )
        Text(
            text= content,
            style = TextStyle(
                fontFamily = Jersey25,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = LightGreyText
            )
        )
    }
}