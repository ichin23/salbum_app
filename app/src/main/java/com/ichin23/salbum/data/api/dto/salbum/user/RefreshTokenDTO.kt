package com.ichin23.salbum.data.api.dto.salbum.user

import com.google.gson.annotations.SerializedName

data class RefreshTokenDTO(
    @SerializedName("access_token") val accessToken: Token
)
