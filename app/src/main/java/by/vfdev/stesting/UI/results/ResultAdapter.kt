package by.vfdev.stesting.UI.results

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.RemoteModel.UsersResult

class ResultAdapter(val list: MutableList<UsersResult?>, val fragment: MyResultsFragment):
    RecyclerView.Adapter<ResultAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvScore: TextView
        init {
            tvScore = itemView.findViewById(R.id.tvMyResultScore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_layout, parent, false)
        val holder = ViewHolder(itemView)

        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tvData).text = list[position]?.date.toString()
        holder.itemView.findViewById<TextView>(R.id.tvMyResultScore).text = list[position]?.scores.toString() + " / 10"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}