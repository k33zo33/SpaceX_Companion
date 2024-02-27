package hr.k33zo.spacex.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_LAUNCHES
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_LAUNCH_PADS
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_MISSIONS
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_ROCKETS
import hr.k33zo.spacex.model.LaunchItem
import hr.k33zo.spacex.model.LaunchPadItem
import hr.k33zo.spacex.model.MissionItem
import hr.k33zo.spacex.model.RocketItem
import java.text.SimpleDateFormat
import java.util.Locale


fun View.applyAnimation(animationId: Int) = startAnimation(
    AnimationUtils.loadAnimation(context, animationId)
)

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() =
    sendBroadcast(Intent(this, T::class.java))

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(
        Intent(this, T::class.java)
        .apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    )

inline fun <reified T : Activity> Context.startActivity(key: String, value: Int) =
    startActivity(
        Intent(this, T::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(key, value)
            }
    )

fun Context.setBooleanPreference(key: String, value: Boolean = true)
    = PreferenceManager.getDefaultSharedPreferences(this)
    .edit()
    .putBoolean(key,value)
    .apply()

fun Context.getBooleanPreference(key: String)
        = PreferenceManager.getDefaultSharedPreferences(this)
    .getBoolean(key, false)

fun callDelayed(delay: Long, work: ()-> Unit){
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}
fun Context.isOnline():Boolean{
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let {network ->
        connectivityManager.getNetworkCapabilities(network)?.let {networkCapabilities ->
            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

fun Context.fetchLaunches(): MutableList<LaunchItem>{

    val launches = mutableListOf<LaunchItem>()
    val cursor = contentResolver?.query(
        SPACEX_PROVIDER_CONTENT_URI_LAUNCHES,
        null,
        null,
        null,
        null
    )
    while (cursor != null && cursor.moveToNext()){
        launches.add(
            LaunchItem(
                cursor.getLong(cursor.getColumnIndexOrThrow(LaunchItem::_id.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(LaunchItem::flight_number.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchItem::mission_name.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchItem::launch_date_utc.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(LaunchItem::launch_success.name))==1,
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchItem::mission_patch.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchItem::details.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchItem::youtube_id.name))
            )
        )
    }
    return launches
}

fun Context.fetchMissions(): MutableList<MissionItem>{

    val missions = mutableListOf<MissionItem>()
    val cursor = contentResolver?.query(
        SPACEX_PROVIDER_CONTENT_URI_MISSIONS,
        null,
        null,
        null,
        null
    )
    while (cursor != null && cursor.moveToNext()){
        missions.add(
            MissionItem(
                cursor.getLong(cursor.getColumnIndexOrThrow(MissionItem::_id.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(MissionItem::mission_name.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(MissionItem::website.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(MissionItem::wikipedia.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(MissionItem::description.name))
            )
        )
    }
    return missions
}

fun Context.fetchRockets(): MutableList<RocketItem>{

    val rockets = mutableListOf<RocketItem>()
    val cursor = contentResolver?.query(
        SPACEX_PROVIDER_CONTENT_URI_ROCKETS,
        null,
        null,
        null,
        null
    )
    while (cursor != null && cursor.moveToNext()){
        rockets.add(
            RocketItem(
                cursor.getLong(cursor.getColumnIndexOrThrow(RocketItem::_id.name)),
                cursor.getInt(cursor.getColumnIndexOrThrow(RocketItem::active.name))==1,
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::country.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::company.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::wikipedia.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::description.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::rocket_name.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::rocket_type.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(RocketItem::flickr_images.name)),

            )
        )
    }
    return rockets
}

fun Context.fetchLaunchPads(): MutableList<LaunchPadItem>{

    val launchPads = mutableListOf<LaunchPadItem>()
    val cursor = contentResolver?.query(
        SPACEX_PROVIDER_CONTENT_URI_LAUNCH_PADS,
        null,
        null,
        null,
        null
    )
    while (cursor != null && cursor.moveToNext()){
        launchPads.add(
            LaunchPadItem(
                cursor.getLong(cursor.getColumnIndexOrThrow(LaunchPadItem::_id.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchPadItem::status.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchPadItem::wikipedia.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchPadItem::details.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchPadItem::site_name_long.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchPadItem::name.name)),
                cursor.getString(cursor.getColumnIndexOrThrow(LaunchPadItem::region.name)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(LaunchPadItem::latitude.name)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(LaunchPadItem::longitude.name)),
                )
        )
    }
    return launchPads
}

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}




