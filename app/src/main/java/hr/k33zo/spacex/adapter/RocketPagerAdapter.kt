package hr.k33zo.spacex.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.k33zo.spacex.R
import hr.k33zo.spacex.model.RocketItem
import java.io.File

private const val MAX_WIDTH = 800
private const val MAX_HEIGHT = 600

class RocketPagerAdapter(
    private val context: Context,
    private val rocketItems: MutableList<RocketItem>
) : RecyclerView.Adapter<RocketPagerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val ivRocket = itemView.findViewById<ImageView>(R.id.ivRocket)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val ivActive = itemView.findViewById<ImageView>(R.id.ivActive)
        private val tvType = itemView.findViewById<TextView>(R.id.tvType)
        private val tvCountry = itemView.findViewById<TextView>(R.id.tvCountry)
        private val tvCompany = itemView.findViewById<TextView>(R.id.tvCompany)
        val tvWikipedia = itemView.findViewById<TextView>(R.id.tvWikipedia)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        fun bind(rocket: RocketItem){
            tvName.text = rocket.rocket_name
            tvType.text = rocket.rocket_type
            tvCountry.text = rocket.country
            tvCompany.text = rocket.company
            tvWikipedia.text = rocket.wikipedia
            tvDescription.text = rocket.description
            ivActive.setImageResource(if(rocket.active)R.drawable.thumb_up_icon
            else R.drawable.thumb_down_icon)


            Picasso.get()
                .load(File(rocket.flickr_images))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .onlyScaleDown()
                .error(R.drawable.spacex)
               //.transform(RoundedCornersTransformation(50,50))
                .into(ivRocket)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(context).inflate(R.layout.rocket_pager, parent, false)
       )
    }

    override fun getItemCount()=rocketItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rocket = rocketItems[position]


        holder.itemView.findViewById<View>(R.id.ivShare).setOnClickListener {
            shareRocketItem(rocket)
        }

        holder.tvWikipedia.setOnClickListener {

            val webUrl = rocket.wikipedia
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            if (intent.resolveActivity(context.packageManager)!=null){
                context.startActivity(intent)
            }
        }
        holder.bind(rocketItems[position])
    }

    private fun shareRocketItem(rocket: RocketItem) {
        val message = StringBuilder().apply {
            append("Rocket Name: ${rocket.rocket_name}\n")
            append("Type: ${rocket.rocket_type}\n")
            append("Country: ${rocket.country}\n")
            append("Company: ${rocket.company}\n")
            append("Description: ${rocket.description}\n")
        }.toString()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share Rocket Item"))
    }
}