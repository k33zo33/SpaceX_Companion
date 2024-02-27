package hr.k33zo.spacex.model

data class MissionItem(
    var _id: Long?,
    val mission_name : String,
    val wikipedia : String?,
    val website : String?,
    val description : String?
)
