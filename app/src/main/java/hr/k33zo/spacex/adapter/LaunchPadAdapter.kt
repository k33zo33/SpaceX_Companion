package hr.k33zo.spacex.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.k33zo.spacex.activity.LAUCH_PADS_POSITION
import hr.k33zo.spacex.activity.LaunchPadsPagerActivity
import hr.k33zo.spacex.R
import hr.k33zo.spacex.framework.startActivity
import hr.k33zo.spacex.model.LaunchPadItem

class LaunchPadAdapter(
    private val context: Context,
    private val launchPadItems: MutableList<LaunchPadItem>
) : RecyclerView.Adapter<LaunchPadAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(launchPad:LaunchPadItem){
            tvTitle.text = launchPad.site_name_long
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchPadAdapter.ViewHolder {
        return LaunchPadAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.launch_pad, parent, false)
        )
    }



    override fun getItemCount()=launchPadItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launchPad = launchPadItems[position]

        holder.itemView.setOnClickListener {
            context.startActivity<LaunchPadsPagerActivity>(LAUCH_PADS_POSITION, position)
        }

        holder.bind(launchPad)
    }


}