package hr.k33zo.spacex.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_LAUNCHES
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_LAUNCH_PADS
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_MISSIONS
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_ROCKETS
import hr.k33zo.spacex.activity.SpaceXReceiver
import hr.k33zo.spacex.framework.sendBroadcast
import hr.k33zo.spacex.handler.downloadImageAndStore
import hr.k33zo.spacex.model.LaunchItem
import hr.k33zo.spacex.model.LaunchPadItem
import hr.k33zo.spacex.model.MissionItem
import hr.k33zo.spacex.model.RocketItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpaceXFetcher(private val context: Context) {
    private val spacexApi: SpaceXApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        spacexApi = retrofit.create(SpaceXApi::class.java)
    }

    fun fetchLaunches(count: Int){
        //background
        val request = spacexApi.fetchLaunches(limit = 10, order = "desc")
        request.enqueue(object : Callback<List<Launch>>{
            override fun onResponse(
                call: Call<List<Launch>>,
                response: Response<List<Launch>>
            ) {
                response.body()?.let { populateLaunches(it) }
            }
            override fun onFailure(call: Call<List<Launch>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    fun fetchMissions(count: Int){
        //background
        val request = spacexApi.fetchMissions(limit = 10, order = "desc")
        request.enqueue(object : Callback<List<Mission>>{
            override fun onResponse(
                call: Call<List<Mission>>,
                response: Response<List<Mission>>
            ) {
                response.body()?.let { populateMissions(it) }
            }
            override fun onFailure(call: Call<List<Mission>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    fun fetchRockets(count: Int){
        //background
        val request = spacexApi.fetchRockets(limit = 10, order = "desc")
        request.enqueue(object : Callback<List<Rocket>>{
            override fun onResponse(
                call: Call<List<Rocket>>,
                response: Response<List<Rocket>>
            ) {
                response.body()?.let { populateRockets(it) }
            }
            override fun onFailure(call: Call<List<Rocket>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    fun fetchLaunchPads(count: Int){
        //background
        val request = spacexApi.fetchLaunchPads(limit = 10, order = "desc")
        request.enqueue(object : Callback<List<LaunchPad>>{
            override fun onResponse(
                call: Call<List<LaunchPad>>,
                response: Response<List<LaunchPad>>
            ) {
                response.body()?.let { populateLaunchPads(it) }
            }
            override fun onFailure(call: Call<List<LaunchPad>>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }




    private fun populateLaunches(launches: List<Launch>) {
        //foreground
        //val items = mutableListOf<Item>()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            launches.forEach{
                val missionPatch = it.links.mission_patch
                if (missionPatch!=null) {
                    val missionPatchPath = downloadImageAndStore(context, it.mission_patch)
                    val values = ContentValues().apply {
                        put(LaunchItem::flight_number.name, it.flight_number)
                        put(LaunchItem::mission_name.name, it.mission_name)
                        put(LaunchItem::launch_date_utc.name, it.launch_date_utc)
                        put(LaunchItem::launch_success.name, it.launch_success)
                        put(LaunchItem::mission_patch.name, missionPatchPath ?: "")
                        put(LaunchItem::details.name, it.details)
                        put(LaunchItem::youtube_id.name, it.links.youtube_id)
                    }
                    context.contentResolver.insert(SPACEX_PROVIDER_CONTENT_URI_LAUNCHES, values)
                }
            }
            //println(items)
            context.sendBroadcast<SpaceXReceiver>()
        }
    }

    private fun populateMissions(launches: List<Mission>) {
        //foreground
        //val items = mutableListOf<Item>()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            launches.forEach{
                    val values = ContentValues().apply {
                        put(MissionItem::mission_name.name, it.mission_name)
                        put(MissionItem::wikipedia.name, it.wikipedia)
                        put(MissionItem::website.name, it.website)
                        put(MissionItem::description.name, it.description)
                    }
                    context.contentResolver.insert(SPACEX_PROVIDER_CONTENT_URI_MISSIONS, values)

            }
            //println(items)
            context.sendBroadcast<SpaceXReceiver>()
        }
    }

    private fun populateRockets(rockets: List<Rocket>) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            rockets.forEach{
                val flickrImage = downloadImageAndStore(context, it.firstFlickrImage)
                val values = ContentValues().apply {
                    put(RocketItem::rocket_name.name, it.rocket_name)
                    put(RocketItem::rocket_type.name, it.rocket_type)
                    put(RocketItem::active.name,it.active)
                    put(RocketItem::company.name, it.company)
                    put(RocketItem::country.name, it.country)
                    put(RocketItem::wikipedia.name, it.wikipedia)
                    put(RocketItem::description.name, it.description)
                    put(RocketItem::flickr_images.name, flickrImage ?:"")
                }
                context.contentResolver.insert(SPACEX_PROVIDER_CONTENT_URI_ROCKETS, values)
            }
        }
    }

    private fun populateLaunchPads(launchPads: List<LaunchPad>) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            launchPads.forEach {
                val values = ContentValues().apply {
                    put(LaunchPadItem::status.name, it.status)
                    put(LaunchPadItem::wikipedia.name, it.wikipedia)
                    put(LaunchPadItem::details.name, it.details)
                    put(LaunchPadItem::site_name_long.name, it.site_name_long)
                    put(LaunchPadItem::details.name, it.details)
                    put(LaunchPadItem::name.name, it.location.name)
                    put(LaunchPadItem::region.name, it.location.region)
                    put(LaunchPadItem::latitude.name, it.location.latitude)
                    put(LaunchPadItem::longitude.name, it.location.longitude)
                }
                context.contentResolver.insert(SPACEX_PROVIDER_CONTENT_URI_LAUNCH_PADS, values)
            }
        }
    }

}


