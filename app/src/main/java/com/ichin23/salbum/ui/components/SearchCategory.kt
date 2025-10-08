package com.ichin23.salbum.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ichin23.salbum.domain.models.Category
import com.ichin23.salbum.ui.theme.BluePrimary
import com.ichin23.salbum.ui.theme.GreyText


@Composable
fun SearchCategory(category: Category, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(8.dp))
            .background( if (isSelected) BluePrimary else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isSelected) BluePrimary else GreyText,
                shape = RoundedCornerShape(8.dp)
            ).padding(10.dp, 6.dp),
    ) {
        Text(category.name, color = if (isSelected) Color.White else GreyText)
    }
}


@Preview
@Composable
fun SearchCategoryPreview() {
    SearchCategory(Category("Álbum", "album"), false, {})
}

@Preview
@Composable
fun SearchCategorySelectedPreview() {
    SearchCategory(Category("Álbum", "album"), true, {})
}