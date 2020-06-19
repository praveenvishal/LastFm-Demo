package com.android.domain.entities

class Artist {
    var name: String? = null
    var playcount: String? = null
    var mbid: String? = null
    var image: List<Image>? = null
    var streamable: String? = null
    var listeners:String?=null
    var url: String? = null
    val imageUrl: String?
        get() {
           val largeImageList= image?.filter { image ->
                image.getSize().equals("large")
            }
            return largeImageList?.get(0)?.url
        }
}