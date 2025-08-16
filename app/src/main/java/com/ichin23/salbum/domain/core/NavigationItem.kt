package com.ichin23.salbum.domain.core

data class NavigationItem(
    val title: String,
    val icon_unselected: Int,
    val icon_selected: Int,
    val route: String
)
