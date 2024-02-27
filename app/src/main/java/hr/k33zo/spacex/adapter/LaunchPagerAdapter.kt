package hr.k33zo.spacex.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.k33zo.spacex.R
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_LAUNCHES
import hr.k33zo.spacex.framework.formatDate
import hr.k33zo.spacex.model.LaunchItem
import java.io.File

private const val MAX_WIDTH = 1000
private const val MAX_HEIGHT = 800
private const val MIME_TYPE = "text/plain"
private const val MIME_TYPE_YT = "text/html"


class LaunchPagerAdapter (
    private val context: Context,
    private val launchItems: MutableList<LaunchItem>
) : RecyclerView.Adapter<LaunchPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivLaunch = itemView.findViewById<ImageView>(R.id.ivLaunch)
        val ivSuccess = itemView.findViewById<ImageView>(R.id.ivSuccess)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvDetails = itemView.findViewById<TextView>(R.id.tvDetails)
        val vwYoutube = itemView.findViewById<WebView>(R.id.wvYoutube)
        fun bind(launch: LaunchItem){
                val formattedDate = formatDate(launch.launch_date_utc)
                val youtubeId = launch.youtube_id
                tvDate.text = formattedDate
                tvDetails.text = launch.details
                ivSuccess.setImageResource(if(launch.launch_success) R.drawable.thumb_up_icon
                    else R.drawable.thumb_down_icon)
                tvTitle.text = launch.mission_name
            if (!youtubeId.isNullOrEmpty()){
                loadYoutube(youtubeId)
            }
           Picasso.get()
                .load(File(launch.mission_patch))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .onlyScaleDown()
                .error(R.drawable.spacex)
                //.transform(RoundedCornersTransformation(50, 50))
                .into(ivLaunch)
        }

        private fun loadYoutube(youtubeId: String?) {
            val video : String = "<iframe width=\"100%\" height=\"100%\"" +
                    " src=\"https://www.youtube.com/embed/${youtubeId}\" title=\"YouTube video player\"" +
                    " frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media;" +
                    " gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"

            vwYoutube.loadData(video,MIME_TYPE_YT, "utf-8")
            vwYoutube.settings.javaScriptEnabled = true
            vwYoutube.webChromeClient = WebChromeClient()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(context).inflate(R.layout.launch_pager, parent, false)
       )
    }

    override fun getItemCount()=launchItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launchItems[position]

        holder.itemView.findViewById<View>(R.id.ivShare).setOnClickListener {
            shareLaunchItem(launch)
        }

        holder.ivSuccess.setOnLongClickListener() {
            //delete

            launch.launch_success = !launch.launch_success
            context.contentResolver.update(
                  ContentUris.withAppendedId(SPACEX_PROVIDER_CONTENT_URI_LAUNCHES, launch._id!!),
                    ContentValues().apply {
                        put(LaunchItem::launch_success.name, launch.launch_success)
                    },
                null,
                null
            )
            notifyItemChanged(position)

            true
        }
        holder.bind(launchItems[position])
    }

    private fun shareLaunchItem(launch: LaunchItem) {
        val message = StringBuilder().apply {
            append("Launch Name: ${launch.mission_name}\n")
            append("Date: ${launch.launch_date_utc}\n")
            append("Details: ${launch.details}\n")
            append("Success: ${if (launch.launch_success) "Yes" else "No"}\n")
        }.toString()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = MIME_TYPE
            putExtra(Intent.EXTRA_TEXT, message)
        }

        context.startActivity(Intent.createChooser(shareIntent,
            context.getString(R.string.share_launch_item)))

    }


}