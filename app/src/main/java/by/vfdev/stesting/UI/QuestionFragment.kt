package by.vfdev.stesting.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import by.vfdev.stesting.Common.Common
import by.vfdev.stesting.Common.CurrentQuestion
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.ViewModel.QuestionViewModel

class QuestionFragment : Fragment(), IAnswerSelect {

    lateinit var questionText: TextView
    lateinit var rbAnswerA: RadioButton
    lateinit var rbAnswerB: RadioButton
    lateinit var rbAnswerC: RadioButton
    lateinit var rbAnswerD: RadioButton
    lateinit var rbAnswerE: RadioButton
    lateinit var layoutImage: FrameLayout
    lateinit var progressBar: ProgressBar
    lateinit var viewModel: QuestionViewModel

    var question: Question? = null
    var questionIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val itemView = inflater.inflate(R.layout.fragment_question, container, false)
        viewModel = ViewModelProvider(activity as MainActivity).get(QuestionViewModel::class.java)

        questionIndex = requireArguments().getInt("index", -1)
        layoutImage = itemView.findViewById(R.id.layout_image) as FrameLayout
        question = viewModel.questionList[questionIndex]
        if (question != null) {
            progressBar = itemView.findViewById(R.id.progressBar) as ProgressBar
           // if (viewModel.questionList. != null)

        }
        questionText = itemView.findViewById(R.id.textQuestion) as TextView
        questionText.text = question!!.QuestionText

        rbAnswerA = itemView.findViewById(R.id.rbAnsA) as RadioButton
        rbAnswerA.text = question!!.AnswerA
        rbAnswerB = itemView.findViewById(R.id.rbAnsB) as RadioButton
        rbAnswerB.text = question!!.AnswerB
        rbAnswerC = itemView.findViewById(R.id.rbAnsC) as RadioButton
        rbAnswerC.text = question!!.AnswerC
        rbAnswerD = itemView.findViewById(R.id.rbAnsD) as RadioButton
        rbAnswerD.text = question!!.AnswerD
        rbAnswerE = itemView.findViewById(R.id.rbAnsE) as RadioButton
        rbAnswerE.text = question!!.AnswerE

        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun selectedAnswer(): CurrentQuestion {
        TODO("Not yet implemented")
    }

    override fun showCorrectAnswer() {
        TODO("Not yet implemented")
    }

    override fun disableAnswer() {
        rbAnswerA.isEnabled = false
        rbAnswerB.isEnabled = false
        rbAnswerC.isEnabled = false
        rbAnswerD.isEnabled = false
        rbAnswerE.isEnabled = false
    }

    override fun resetQuestion() {
        rbAnswerA.isEnabled = true
        rbAnswerB.isEnabled = true
        rbAnswerC.isEnabled = true
        rbAnswerD.isEnabled = true
        rbAnswerE.isEnabled = true
    }
}