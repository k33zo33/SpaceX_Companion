package hr.k33zo.spacex.model

data class LaunchPadItem(
    var _id : Long?,
    val status : String,
    val wikipedia : String,
    val details : String,
    val site_name_long : String,
    val name : String,
    val region : String,
    val latitude : Double,
    val longitude : Double
)
