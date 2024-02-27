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
import hr.k33zo.spacex.activity.POSITION
import hr.k33zo.spacex.R
import hr.k33zo.spacex.activity.RocketsPagerActivity
import hr.k33zo.spacex.activity.SPACEX_PROVIDER_CONTENT_URI_ROCKETS
import hr.k33zo.spacex.framework.startActivity
import hr.k33zo.spacex.model.RocketItem
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File


private const val MAX_WIDTH = 1000
private const val MAX_HEIGHT = 600

class RocketAdapter(
    private val context: Context,
    private val rocketItems: MutableList<RocketItem>
) : RecyclerView.Adapter<RocketAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val ivRocket = itemView.findViewById<ImageView>(R.id.ivRocket)
        fun bind(rocket:RocketItem){
            tvTitle.text = rocket.rocket_name
            Picasso.get()
                .load(File(rocket.flickr_images))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                //.onlyScaleDown()
                .error(R.drawable.spacex)
                .transform(RoundedCornersTransformation(15,15))
                .into(ivRocket)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.rocket,parent,false)
        )
    }

    override fun getItemCount()=rocketItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rocket = rocketItems[position]
        holder.itemView.setOnLongClickListener(){

            context.contentResolver.delete(
                ContentUris.withAppendedId(
                    SPACEX_PROVIDER_CONTENT_URI_ROCKETS, rocket._id!!),
                null,
                null
            )
            rocketItems.removeAt(position)
            notifyDataSetChanged()

            true
        }
        holder.itemView.setOnClickListener {
            context.startActivity<RocketsPagerActivity>(POSITION,position)
        }

        holder.bind(rocket)
    }
}