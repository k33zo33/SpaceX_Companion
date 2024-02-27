package hr.k33zo.spacex.api

import com.google.gson.annotations.SerializedName

data class Mission(
    @SerializedName("mission_name") val mission_name : String,
    @SerializedName("wikipedia") val wikipedia : String,
    @SerializedName("website") val website : String,
    @SerializedName("description") val description : String
)

