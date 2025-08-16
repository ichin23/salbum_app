package com.ichin23.salbum.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ichin23.salbum.domain.core.NavigationItem
import com.ichin23.salbum.R
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.theme.BluePrimary
import com.ichin23.salbum.ui.theme.WhiteText

@Composable
fun BottomNavigationBar(navController: NavHostController, enableSearchFocus: ()->Unit, modifier: Modifier = Modifier) {
    val navigationList = arrayListOf(
        NavigationItem(
            title = "Home",
            icon_selected = R.drawable.home_filled,
            icon_unselected = R.drawable.home_outlined,
            route = ScreenName.HOME_SCREEN
        ),
        NavigationItem(
            title = "Search",
            icon_selected = R.drawable.lupa_outlined,
            icon_unselected = R.drawable.lupa_outlined,
            route = ScreenName.SEARCH_SCREEN
        ),
        NavigationItem(
            title = "Profile",
            icon_selected = R.drawable.person_filled,
            icon_unselected = R.drawable.person_outlined,
            route = ScreenName.PROFILE_SCREEN
        ),
    )

    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        navigationList.forEachIndexed {index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    if (selectedNavigationIndex.intValue==1&&index==1){
                        enableSearchFocus()
                    }
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route){
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    if (selectedNavigationIndex.intValue == index) {
                        Image(
                            imageVector = ImageVector.vectorResource(item.icon_selected),
                            contentDescription = item.title,
                            modifier = Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(BluePrimary),
                        )
                    } else {
                        Image(
                            imageVector = ImageVector.vectorResource(item.icon_unselected),
                            contentDescription = item.title,
                            modifier = Modifier.size(18.dp),
                            colorFilter = ColorFilter.tint(WhiteText),
                        )
                    }
                },
                label = {
                    Text(
                        item.title,
                        color = if (index == selectedNavigationIndex.intValue)
                            BluePrimary
                        else WhiteText,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Transparent,
                    indicatorColor = Color.Transparent,
                ),
            )
        }
    }
}