package hr.k33zo.spacex.api

import com.google.gson.annotations.SerializedName


data class Rocket(
    @SerializedName("id") val id: Int,
    @SerializedName("active") val active: Boolean,
    @SerializedName("country") val country: String,
    @SerializedName("company") val company: String,
    @SerializedName("wikipedia") val wikipedia: String,
    @SerializedName("description") val description: String,
    @SerializedName("rocket_name") val rocket_name: String,
    @SerializedName("rocket_type") val rocket_type: String,
    @SerializedName("flickr_images") private val flickrImages: List<String>
) {
    val firstFlickrImage: String
        get() = flickrImages.first()
}

