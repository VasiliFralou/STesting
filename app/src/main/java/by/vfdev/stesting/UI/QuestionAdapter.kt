package by.vfdev.stesting.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question

class QuestionAdapter (private val questionList: ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.MyViewHolder> () {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val questionText = itemView.findViewById<TextView>(R.id.tvQuestion)
        val answerA = itemView.findViewById<TextView>(R.id.rbAnsA)
        val answerB = itemView.findViewById<TextView>(R.id.rbAnsB)
        val answerC = itemView.findViewById<TextView>(R.id.rbAnsC)
        val answerD = itemView.findViewById<TextView>(R.id.rbAnsD)
        val answerE = itemView.findViewById<TextView>(R.id.rbAnsE)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = questionList[position]
        holder.questionText.text = currentitem.QuestionText
        holder.answerA.text = currentitem.AnswerA
        holder.answerB.text = currentitem.AnswerB
        holder.answerC.text = currentitem.AnswerC
        holder.answerD.text = currentitem.AnswerD

        // ??????????????????????????????????????????
        if (currentitem.AnswerD == "") {
            holder.answerD.isVisible = false
        }

        holder.answerE.text = currentitem.AnswerE
        if (currentitem.AnswerE == "") {
            holder.answerE.isVisible = false
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}