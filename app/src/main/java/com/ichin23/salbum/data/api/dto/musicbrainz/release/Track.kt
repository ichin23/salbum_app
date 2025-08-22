package com.ichin23.salbum.data.api.dto.musicbrainz.release

data class Track(
    val length: Int,
    val number: Int,
    val position: Int,
    val title:String,
    val recording: Recording,
    val artistCredit: List<ArtistCreditLookup>
)
