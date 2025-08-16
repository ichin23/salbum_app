package com.ichin23.salbum.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    val id:String,
    val name:String,
    val username:String,
    @SerializedName("image_url")
    val imageUrl:String?,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
)
