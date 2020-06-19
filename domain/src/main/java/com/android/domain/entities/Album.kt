package com.android.domain.entities

class Album {
    var mbid: String? = null
    var name: String? = null
    var image: List<Image>? = null
    var playcount: String? = null
    var artist: Artist? = null
    var url: String? = null
    val imageUrl: String?
        get() {
            val largeImageList= image?.filter {
                     image -> image.getSize().equals("large")
             }
           return largeImageList?.get(0)?.url
        }
}