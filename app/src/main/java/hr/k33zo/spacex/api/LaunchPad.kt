package hr.k33zo.spacex.api

import com.google.gson.annotations.SerializedName

data class LaunchPad(
    @SerializedName("status") val status : String,
    @SerializedName("location") val location : Location,
    @SerializedName("wikipedia") val wikipedia : String,
    @SerializedName("details") val details : String,
    @SerializedName("site_name_long") val site_name_long : String
){
    val name: String
        get() = location.name
    val region: String
        get() = location.region
    val latitude : Double
        get() = location.latitude
    val longitude : Double
        get() = location.longitude
}


data class Location(
    @SerializedName("name") val name : String,
    @SerializedName("region") val region : String,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)