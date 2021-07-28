package by.vfdev.stesting.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.question_item.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.sign

class QuestionActivity : AppCompatActivity() {

    lateinit var viewModel: QuestionViewModel
    private lateinit var tvTimer: TextView

    var timeTest = 20*60*1000
    private var mCurrentPosition: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)

        initViews()
        timerTest()

        setQuestion()

        Log.d("VM", viewModel.questionList.toString())
    }

    private fun timerTest() {
        object: CountDownTimer(timeTest.toLong(),1000) {
            @SuppressLint("DefaultLocale")
            override fun onTick(interval: Long) {
                tvTimer.text = String.format("%02d:%02d", interval / 60000, interval % 60000 / 1000)
                timeTest -= 1000
            }
            override fun onFinish() {

            }
        }.start()
    }

    private fun initViews() {
        tvTimer = findViewById(R.id.tvTimer)
    }

    private fun setQuestion() {
        val rnds = (0..9).random()
        val question = viewModel.questionList.get((mCurrentPosition - 1) + rnds)
        Log.d("!!!", (viewModel.questionList.get(mCurrentPosition + 1)).toString())

        if (mCurrentPosition == viewModel.questionList.size)
            btnNext.text = "FINISH"
        progressBar.progress = mCurrentPosition
        rightAns.text = "$mCurrentPosition / ${progressBar.max}"

        tvQuestion.text = question.QuestionText
        // imgQuestion.setImageResource(question.QuestionImage)
        rbAnsA.text = question.AnswerA
        rbAnsB.text = question.AnswerB
        if (question.AnswerC == null)
            rbAnsC.isVisible = false
        else rbAnsC.text = question.AnswerC
        if (question.AnswerD == null)
            rbAnsD.isVisible = false
        else rbAnsD.text = question.AnswerD
        if (question.AnswerE == null)
            rbAnsE.isVisible = false
        else rbAnsE.text = question.AnswerE
    }

    fun nextQuestion(view: View) {
        mCurrentPosition++
        rightAns.text = "$mCurrentPosition / 10"

        val rnds = (0..9).random()
        val question = viewModel.questionList.get((mCurrentPosition - 1) + rnds)

    }
}