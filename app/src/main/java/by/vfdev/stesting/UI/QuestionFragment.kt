package by.vfdev.stesting.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import by.vfdev.stesting.Common.Common
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question

class QuestionFragment : Fragment() {

    lateinit var questionText: TextView
    lateinit var rbAnswerA: RadioButton
    lateinit var rbAnswerB: RadioButton
    lateinit var rbAnswerC: RadioButton
    lateinit var rbAnswerD: RadioButton
    lateinit var rbAnswerE: RadioButton
    lateinit var layoutImage: FrameLayout
    lateinit var progressBar: ProgressBar

    var question: Question? = null
    var questionIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView = inflater.inflate(R.layout.fragment_question, container, false)
        questionIndex = requireArguments().getInt("index", -1)
        layoutImage = itemView.findViewById(R.id.layout_image) as FrameLayout
        question = questionList

    }
}