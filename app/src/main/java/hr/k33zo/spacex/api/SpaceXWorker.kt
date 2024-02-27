package hr.k33zo.spacex.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SpaceXWorker(private val context: Context, workerParams: WorkerParameters
    ):Worker(context, workerParams) {
    override fun doWork(): Result {
        SpaceXFetcher(context).fetchLaunches(10)
        SpaceXFetcher(context).fetchMissions(10)
        SpaceXFetcher(context).fetchRockets(10)
        SpaceXFetcher(context).fetchLaunchPads(10)

        return Result.success()
    }
}