package com.android.photoapp.data.response

import com.google.gson.annotations.SerializedName


data class User(

    @SerializedName("id") var id: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("username") var username: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("twitter_username") var twitterUsername: String? = null,
    @SerializedName("portfolio_url") var portfolioUrl: String? = null,
    @SerializedName("bio") var bio: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("profile_image") var profileImage: ProfileImage? = ProfileImage(),
    @SerializedName("instagram_username") var instagramUsername: String? = null,
    @SerializedName("total_collections") var totalCollections: Int? = null,
    @SerializedName("total_likes") var totalLikes: Int? = null,
    @SerializedName("total_photos") var totalPhotos: Int? = null,
    @SerializedName("total_promoted_photos") var totalPromotedPhotos: Int? = null,
    @SerializedName("total_illustrations") var totalIllustrations: Int? = null,
    @SerializedName("total_promoted_illustrations") var totalPromotedIllustrations: Int? = null,
    @SerializedName("accepted_tos") var acceptedTos: Boolean? = null,
    @SerializedName("for_hire") var forHire: Boolean? = null,
    @SerializedName("social") var social: Social? = Social()

)