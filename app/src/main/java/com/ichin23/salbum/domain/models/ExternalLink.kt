package com.ichin23.salbum.domain.models

class ExternalLink {
    var source: String
    var href: String

    constructor(
        href:String
    ){
        this.href = href;
        this.source=href;

    }
}