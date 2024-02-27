package hr.k33zo.spacex.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "https://api.spacexdata.com/v3/"

interface SpaceXApi {
    @GET("launches")
    fun fetchLaunches(@Query("limit") limit: Int = 10, @Query("order") order: String="desc")
        : Call<List<Launch>>

    @GET("missions")
    fun fetchMissions(@Query("limit") limit: Int = 10, @Query("order") order: String="desc")
        : Call<List<Mission>>

    @GET("rockets")
    fun fetchRockets(@Query("limit") limit: Int = 10, @Query("order") order: String="desc")
            : Call<List<Rocket>>

    @GET("launchpads")
    fun fetchLaunchPads(@Query("limit") limit: Int = 10, @Query("order") order: String="desc")
            : Call<List<LaunchPad>>

}