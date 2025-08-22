package com.ichin23.salbum.data.api.dto.musicbrainz

import com.google.gson.annotations.SerializedName
import com.ichin23.salbum.data.api.dto.musicbrainz.releaseGroup.ReleaseGroup

data class ReleaseGroupDTO(
    val count: Int,
    @SerializedName("release-groups")
    val releaseGroups: List<ReleaseGroup>
)
