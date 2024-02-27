package hr.k33zo.spacex.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.k33zo.spacex.activity.MISSIONS_POSITION
import hr.k33zo.spacex.activity.MissionsPagerActivity
import hr.k33zo.spacex.R
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_MISSIONS
import hr.k33zo.spacex.framework.startActivity
import hr.k33zo.spacex.model.MissionItem

class MissionAdapter(
    private val context: Context,
    private val missionItems: MutableList<MissionItem>
) : RecyclerView.Adapter<MissionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(mission:MissionItem){
            tvTitle.text = mission.mission_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.mission,parent,false)
        )
    }

    override fun getItemCount()=missionItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mission = missionItems[position]
        holder.itemView.setOnLongClickListener(){

            context.contentResolver.delete(
                ContentUris.withAppendedId(
                    SPACEX_PROVIDER_CONTENT_URI_MISSIONS, mission._id!!),
                null,
                null
                )
            missionItems.removeAt(position)
            notifyDataSetChanged()

            true
        }
        holder.itemView.setOnClickListener() {
            //edit
           context.startActivity<MissionsPagerActivity>(MISSIONS_POSITION, position)

        }
        holder.bind(mission)
    }
}