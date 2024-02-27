package hr.k33zo.spacex.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.k33zo.spacex.R
import hr.k33zo.spacex.model.MissionItem


private const val MIME_TYPE = "text/plain"

class MissionPagerAdapter(
    private val context: Context,
    private val missionItems: MutableList<MissionItem>
) : RecyclerView.Adapter<MissionPagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvWikipedia = itemView.findViewById<TextView>(R.id.tvWikipedia)
        val tvWebsite = itemView.findViewById<TextView>(R.id.tvWebsite)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        fun bind(mission: MissionItem){
            tvName.text = mission.mission_name
            tvWikipedia.text = mission.wikipedia
            tvWebsite.text = mission.website
            tvDescription.text = mission.description

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.mission_pager, parent,false)
        )
    }

    override fun getItemCount()=missionItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mission = missionItems[position]

        holder.tvWikipedia.setOnClickListener {

            val webUrl = mission.wikipedia
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            if (intent.resolveActivity(context.packageManager)!=null){
                context.startActivity(intent)
            }
        }
        holder.tvWebsite.setOnClickListener {

            val webUrl = mission.website
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            if (intent.resolveActivity(context.packageManager)!=null){
                context.startActivity(intent)
            }
        }
        holder.itemView.findViewById<View>(R.id.ivShare).setOnClickListener { 
            shareMissionItem(mission)
        }
        
        holder.bind(missionItems[position])
    }

    private fun shareMissionItem(mission: MissionItem) {
        val message = StringBuilder().apply {
            append("Mission Name: ${mission.mission_name}\n")
            append("Wikipedia: ${mission.wikipedia}\n")
            append("Website: ${mission.website}\n")
            append("Description: ${mission.description}\n")
        }.toString()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = MIME_TYPE
            putExtra(Intent.EXTRA_TEXT, message)
        }
        context.startActivity(Intent.createChooser(shareIntent,
            context.getString(R.string.share_mission)))
    }
}