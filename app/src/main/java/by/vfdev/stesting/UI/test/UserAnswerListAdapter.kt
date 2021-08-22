package by.vfdev.stesting.UI.test

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Answer

class UserAnswerListAdapter(private val userAnswerList: MutableList<Answer?>, val fragment: UserAnswerListFragment):
    RecyclerView.Adapter<UserAnswerListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_answer_layout, parent, false)

        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor", "CutPasteId")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun set() {
            holder.itemView.findViewById<TextView>(R.id.tvTextQuestion).text = userAnswerList[position]?.QuestionText.toString()
            holder.itemView.findViewById<TextView>(R.id.tvUserAnswer).text = userAnswerList[position]?.CurrentAnswer.toString()
        }

        if (userAnswerList[position]?.CurrentAnswer == userAnswerList[position]?.CorrectAnswer) {
            holder.itemView.findViewById<TextView>(R.id.horizontalLine).isVisible = false
            holder.itemView.findViewById<TextView>(R.id.titleCurrentAnswer).isVisible = false
            holder.itemView.findViewById<TextView>(R.id.tvAnswerCorrect).isVisible = false

            holder.itemView.findViewById<TextView>(R.id.tvNumberQuestion).setTextColor(R.color.green)
            holder.itemView.findViewById<TextView>(R.id.tvUserAnswer).setTextColor(R.color.green)
            holder.itemView.findViewById<TextView>(R.id.tvNumberQuestion).text = "Вопрос " + userAnswerList[position]?.IdQuestion.toString() + " - Правильный!"
            set()
        } else {
            holder.itemView.findViewById<TextView>(R.id.horizontalLine).isVisible = true
            holder.itemView.findViewById<TextView>(R.id.titleCurrentAnswer).isVisible = true
            holder.itemView.findViewById<TextView>(R.id.tvAnswerCorrect).isVisible = true

            holder.itemView.findViewById<TextView>(R.id.tvNumberQuestion).setTextColor(R.color.red)
            holder.itemView.findViewById<TextView>(R.id.tvNumberQuestion).text = "Вопрос " + userAnswerList[position]?.IdQuestion.toString() + " - Ошибка!"
            holder.itemView.findViewById<TextView>(R.id.tvAnswerCorrect).text = userAnswerList[position]?.CorrectAnswer.toString()
            set()
        }
    }

    override fun getItemCount(): Int {
        return userAnswerList.size
    }

}