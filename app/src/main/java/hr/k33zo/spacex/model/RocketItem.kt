package hr.k33zo.spacex.model

data class RocketItem(
    var _id: Long?,
    val active: Boolean,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val rocket_name : String,
    val rocket_type : String,
    val flickr_images :String

)
