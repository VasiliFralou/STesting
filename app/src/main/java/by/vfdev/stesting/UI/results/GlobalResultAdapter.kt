package by.vfdev.stesting.UI.results

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.UsersResult

class GlobalResultAdapter(private val GlobalList: MutableList<UsersResult?>, val fragment: GlobalResultsFragment):
    RecyclerView.Adapter<GlobalResultAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_global_layout, parent, false)

        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = GlobalList[position]?.user.toString()
        holder.itemView.findViewById<TextView>(R.id.tvUserGlobal).text = user.substringBefore("@")
        holder.itemView.findViewById<TextView>(R.id.tvDataGlobal).text = GlobalList[position]?.date.toString()
        holder.itemView.findViewById<TextView>(R.id.tvGlobalResultScore).text = GlobalList[position]?.scores.toString() + " / 10"
    }

    override fun getItemCount(): Int {
        return GlobalList.size
    }
}