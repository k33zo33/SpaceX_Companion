package hr.k33zo.spacex.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.k33zo.spacex.framework.setBooleanPreference
import hr.k33zo.spacex.framework.startActivity

class SpaceXReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}