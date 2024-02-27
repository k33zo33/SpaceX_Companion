package hr.k33zo.spacex.model

data class LaunchItem(
    var _id: Long?,
    val flight_number: Int,
    val mission_name: String,
    val launch_date_utc: String,
    var launch_success: Boolean,
    val mission_patch: String?,
    val details: String?,
    val youtube_id: String?
)
