package by.vfdev.stesting.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.activity_question.*
import java.lang.Exception

class QuestionActivity : AppCompatActivity() {

    private lateinit var question: Question
    private lateinit var tvTimer: TextView

    lateinit var viewModel: QuestionViewModel
    lateinit var radioButton: RadioButton

    var radioGroup: RadioGroup? = null

    // Default and the first question position
    private var currentPosition: Int = 1
    private var correctAnswers: Int = 0
    private var rnds: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        viewModel = ViewModelProvider(this,
            MyFactory.getInstance())
            .get(QuestionViewModel::class.java)

        initViews()
        timerTest()
        setQuestion()
    }

    private fun initViews() {
        tvTimer = findViewById(R.id.tvTimer)
        radioGroup = findViewById(R.id.rbGroupQuestion)
    }

    private fun setQuestion() {

        rnds = (0..viewModel.questionList.size).random()
        Log.d("!!!Rnds", rnds.toString())
        question = viewModel.questionList.get(rnds)

        Log.d("!!!Question", question.toString())

        when {
            currentPosition == 10 -> { btnNext.text = "FINISH" }
            currentPosition > 10 -> {
                Log.d("!!!Result: ", "$correctAnswers / 10")
            }
        }

        progressBar.progress = currentPosition
        rightAns.text = "$currentPosition / 10"

        tvQuestion.text = question.QuestionText

        // imgQuestion.setImageResource(question.QuestionImage)

        if (question.AnswerA == "")
            rbAnsA.isVisible = false
        else rbAnsA.text = question.AnswerA

        if (question.AnswerB == "")
            rbAnsB.isVisible = false
        else rbAnsB.text = question.AnswerB

        if (question.AnswerC == "")
            rbAnsC.isVisible = false
        else rbAnsC.text = question.AnswerC

        if (question.AnswerD == "")
            rbAnsD.isVisible = false
        else rbAnsD.text = question.AnswerD

        if (question.AnswerE == "")
            rbAnsE.isVisible = false
        else rbAnsE.text = question.AnswerE
    }

    fun nextQuestion(view: View) {

        if (radioGroup!!.checkedRadioButtonId == 0) {
            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
        }

        currentPosition++

        if (question.CorrectAnswer == radioButton.text) {
            correctAnswers++
            Log.d("!!!", "Правильно!")
        } else {
            Log.d("!!!", "Неправильно!")
        }

        when {
            currentPosition <= viewModel.questionList.size -> {
                setQuestion()
            } else -> {
                Log.d("!!!Result: ", correctAnswers.toString())
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

            }
        }.start()
    }
}