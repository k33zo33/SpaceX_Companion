package hr.k33zo.spacex.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.k33zo.spacex.activity.MapActivity
import hr.k33zo.spacex.R
import hr.k33zo.spacex.model.LaunchPadItem

class LaunchPadPagerAdapter(
    private val context: Context,
    private val launchPadItems: MutableList<LaunchPadItem>
) : RecyclerView.Adapter<LaunchPadPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        val tvWikipedia = itemView.findViewById<TextView>(R.id.tvWikipedia)
        private val tvLocation = itemView.findViewById<TextView>(R.id.tvLocation)
        private val tvRegion = itemView.findViewById<TextView>(R.id.tvRegion)
        private val tvDetails = itemView.findViewById<TextView>(R.id.tvDetails)



        fun bind(launchPad: LaunchPadItem) {
            tvName.text = launchPad.site_name_long
            tvStatus.text = launchPad.status
            tvWikipedia.text = launchPad.wikipedia
            tvLocation.text = launchPad.name
            tvRegion.text = launchPad.region
            tvDetails.text = launchPad.details
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.launch_pad_pager, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = launchPadItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launchPad = launchPadItems[position]

        holder.itemView.findViewById<View>(R.id.ivMap).setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            // Pass latitude and longitude to the MapActivity
            intent.putExtra("latitude", launchPad.latitude)
            intent.putExtra("longitude", launchPad.longitude)
            context.startActivity(intent)
        }

        holder.bind(launchPad)
    }
}
