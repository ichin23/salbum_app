package com.ichin23.salbum.data.api.dto.salbum.listenlist

import com.ichin23.salbum.data.api.dto.salbum.musicmetadata.MusicMetadataCacheDTO
import java.time.LocalDateTime

data class ListenListItem(
    var id: String,
    var itemType: String,
    var created_at: LocalDateTime,
    var item: MusicMetadataCacheDTO,

)
