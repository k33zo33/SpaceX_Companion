package hr.k33zo.spacex.api

import com.google.gson.annotations.SerializedName

data class Launch(
    @SerializedName("flight_number") val flight_number : Int,
    @SerializedName("mission_name") val mission_name : String,
    @SerializedName("launch_date_utc") val launch_date_utc : String,
    @SerializedName("launch_success") val launch_success : Boolean,
    @SerializedName("links") val links : Links,
    @SerializedName("details") val details : String,

){
    val mission_patch: String
        get() = links.mission_patch
    val youtube_id: String
        get() = links.youtube_id
}
data class Links(
    @SerializedName("mission_patch") val mission_patch: String,
    @SerializedName("youtube_id") val youtube_id: String
)

