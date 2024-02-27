package hr.k33zo.spacex.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.k33zo.spacex.activity.LaunchesPagerActivity
import hr.k33zo.spacex.activity.POSITION
import hr.k33zo.spacex.R
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_LAUNCHES
import hr.k33zo.spacex.framework.startActivity
import hr.k33zo.spacex.model.LaunchItem
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

private const val MAX_WIDTH = 800
private const val MAX_HEIGHT = 600

class LaunchAdapter (
    private val context: Context,
    private val launchItems: MutableList<LaunchItem>
) : RecyclerView.Adapter<LaunchAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivLaunch = itemView.findViewById<ImageView>(R.id.ivImage)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(launch: LaunchItem){
                tvTitle.text = launch.mission_name
            Picasso.get()
                .load(File(launch.mission_patch))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .onlyScaleDown()
                .error(R.drawable.spacex)
                .transform(RoundedCornersTransformation(50,50))
                .into(ivLaunch)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(context).inflate(R.layout.launch, parent, false)
       )
    }

    override fun getItemCount()=launchItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launchItems[position]
        holder.itemView.setOnLongClickListener() {
            //delete
            context.contentResolver.delete(
                ContentUris.withAppendedId(
                    SPACEX_PROVIDER_CONTENT_URI_LAUNCHES, launch._id!!),
                null,
                null
            )
            File(launch.mission_patch).delete()
            launchItems.removeAt(position)
            notifyDataSetChanged()


            true
        }
        holder.itemView.setOnClickListener() {
            //edit
            context.startActivity<LaunchesPagerActivity>(POSITION, position)

        }
        holder.bind(launch)
    }

}