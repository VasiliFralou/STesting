package by.vfdev.stesting.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question.view.*
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.view.*


class TestFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    private lateinit var question: Question
    private lateinit var tvTimer: TextView
    private lateinit var rbGroupQuestion: RadioGroup
    private lateinit var viewModel: QuestionViewModel
    private lateinit var answerChecked: String

    lateinit var navController: NavController

    // Default and the first question position
    private var currentPosition: Int = 1
    private var correctAnswers: Int = 0
    private var rnds: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        initViews(view)
        setQuestion()
        timerTest()

        btnNext.setOnClickListener {
            nextQuestion()
        }
        rbGroupQuestion.setOnCheckedChangeListener { radioGroup, id ->
            var rb = view.findViewById<RadioButton>(id)
            if (rb!=null)
                answerChecked = rb.text.toString()
        }
    }

    private fun initViews(view: View) {
        tvTimer = view.findViewById(R.id.tvTimer)
        rbGroupQuestion = view.findViewById(R.id.rbGroupQuestion)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        rbGroupQuestion.clearCheck()

        rnds = (0..9).random()
        progressBar.progress = currentPosition
        rightAns.text = "$currentPosition / 10"

        question = viewModel.questionList[rnds]
        when {
            currentPosition == 10 -> {
                btnNext.text = "FINISH"
            }
            currentPosition > 10 -> {
                Log.d("!!!Result: ", "$correctAnswers / 10")
            }
        }

        tvQuestion.text = question.QuestionText

        if (question.QuestionImage == null) {
        } else {
//            val imgResId = question.QuestionImage
//            var resId = imgResId!!.toInt()
//            imgQuestion.setImageResource(resId)
        }

        if (question.AnswerA == null) rbAnsA.isVisible = false
        else {
            rbAnsA.text = question.AnswerA
            rbAnsA.isVisible = true
        }

        if (question.AnswerB == null) rbAnsB.isVisible = false
        else {
            rbAnsB.text = question.AnswerB
            rbAnsB.isVisible = true
        }

        if (question.AnswerC == null) rbAnsC.isVisible = false
        else {
            rbAnsC.text = question.AnswerC
            rbAnsC.isVisible = true
        }

        if (question.AnswerD == null) rbAnsD.isVisible = false
        else {
            rbAnsD.text = question.AnswerD
            rbAnsD.isVisible = true
        }

        if (question.AnswerE == null) rbAnsE.isVisible = false
        else {
            rbAnsE.text = question.AnswerE
            rbAnsE.isVisible = true
        }
    }

    private fun nextQuestion() {
        // fun
        currentPosition++

        if (question.CorrectAnswer == answerChecked) {
            correctAnswers++
            Log.d("!!!", "Правильно!")
        } else {
            Log.d("!!!", "Неправильно!")
        }
        when {
            currentPosition < 10 -> {
                setQuestion()
            }
            else -> {
                viewModel.resultTest = correctAnswers
                Log.d("!!!Result: ", correctAnswers.toString())
                Log.d("!!!Result VM: ", viewModel.resultTest.toString())
                navController.navigate(R.id.resultTestFragment)
            }
        }
    }

    private fun timerTest() {
        var timeTest = viewModel.TOTAL_TIME
        object: CountDownTimer(timeTest.toLong(),1000) {
            @SuppressLint("DefaultLocale")
            override fun onTick(interval: Long) {
                tvTimer.text = String.format("%02d:%02d",
                    interval / 60000, interval % 60000 / 1000)
                timeTest -= 1000
            }
            override fun onFinish() {
                viewModel.resultTest = correctAnswers
                navController.navigate(R.id.resultTestFragment)
            }
        }.start()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val checkedRadioButton = group?.findViewById(group.checkedRadioButtonId) as? RadioButton
        checkedRadioButton?.let {
            if (checkedRadioButton.isChecked) {
                answerChecked = checkedRadioButton.text as String
                Log.d("!!!AnswerChecked", answerChecked)
            }
        }
    }
}