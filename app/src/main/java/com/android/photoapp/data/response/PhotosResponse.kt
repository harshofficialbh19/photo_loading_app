package com.android.photoapp.data.response

import com.google.gson.annotations.SerializedName


data class PhotosResponse(

    @SerializedName("id") var id: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("alt_description") var altDescription: String? = null,
    @SerializedName("urls") var urls: Urls? = Urls(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("user") var user: User? = User()

)