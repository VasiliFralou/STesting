package by.vfdev.stesting.UI.test

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.room.Room
import by.vfdev.stesting.LocalModel.QuestionImagesDatabase
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Answer
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_test.*
import android.os.CountDownTimer as CountDownTimer
import androidx.core.view.marginBottom as marginBottom

class TestFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    private lateinit var question: Question
    private lateinit var tvTimer: TextView
    private lateinit var rbGroupQuestion: RadioGroup

    lateinit var navController: NavController

    // Default and the first question position
    private var positionQuestion: Int = 0

    private val colorStateList = ColorStateList(arrayOf(
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_enabled)),
        intArrayOf(Color.WHITE , Color.WHITE)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        // Call function start timer
        timerTest(true)

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        val db = Room.databaseBuilder(requireActivity(),
            QuestionImagesDatabase::class.java, "STestingDB.db")
            .createFromAsset("STestingDB.db")
            .allowMainThreadQueries()
            .build()

        viewModel.questionImagesList = db.questionImagesDao().getAll().toMutableList()

        // Call function generation list question
        generationListQuestions()
        initViews(view)
        setNewQuestion(positionQuestion)

        btnNext.setOnClickListener {
            positionQuestion++
            nextQuestion(positionQuestion)
        }
        btnBack.setOnClickListener {
            positionQuestion--
            backQuestion(positionQuestion)
        }
        btnNavMenu.setOnClickListener{ v ->
            (activity as StuffTestingActivity).openCloseNavigationDrawer()
        }
        rbGroupQuestion.setOnCheckedChangeListener { radioGroup, id ->
            val rb = view.findViewById<RadioButton>(id)
            viewModel.newList[positionQuestion]?.CurrentAnswer = rb?.text?.toString() ?: null.toString()
        }
    }

    private fun nextQuestion(positionQuestion: Int) {
        when {
            positionQuestion == 10 -> alertEndTest()
            positionQuestion < 10 -> setNewQuestion(positionQuestion)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun backQuestion(positionQuestion: Int) {
        setNewQuestion(positionQuestion)
    }

    private fun initViews(view: View) {
        tvTimer = view.findViewById(R.id.tvTimer)
        rbGroupQuestion = view.findViewById(R.id.rbGroupQuestion)
    }

    @SuppressLint("SetTextI18n")
    private fun setNewQuestion(positionQuestion: Int) {
        btnBack.isVisible = positionQuestion != 0

        if (positionQuestion == 9) btnNext.text = "Завершить"
        else btnNext.text = "Следующий"

        val positionList = positionQuestion + 1

        val info = arrayListOf(
            viewModel.newList[positionQuestion]?.AnswerA,
            viewModel.newList[positionQuestion]?.AnswerB,
            viewModel.newList[positionQuestion]?.AnswerC,
            viewModel.newList[positionQuestion]?.AnswerD,
            viewModel.newList[positionQuestion]?.AnswerE)

        progressBar.progress = positionList
        rightAns.text = "$positionList / 20"
        tvQuestion.text = viewModel.newList[positionQuestion]?.QuestionText

        checkImage(viewModel.newList[positionQuestion]?.Id)

        val random = info.shuffled()
        rbGroupQuestion.removeAllViews()
        for (i in random.indices) {
            val newButton = RadioButton(requireActivity())
            if (random[i] == null) {
                newButton.isVisible = false
            }
            newButton.text = random[i]
            newButton.tag = i.toString()
            newButton.buttonTintList = colorStateList
            newButton.textSize = 16f
            newButton.typeface = Typeface.createFromAsset(requireActivity().assets, "fonts/reef.otf")

            rbGroupQuestion.addView(newButton)
        }
    }

    private fun generationListQuestions() {

        viewModel.newList.clear()

        val maxSize = viewModel.questionList.size - 1
        for (i in 0..9) {
            val rnds = (0..maxSize).random()
            question = viewModel.questionList[rnds]!!
            viewModel.newList.add(Answer(question.Id,
                i+1, question.QuestionText,
                question.AnswerA, question.AnswerB,
                question.AnswerC, question.AnswerD,
                question.AnswerE, question.CorrectAnswer))
        }
    }

    @SuppressLint("SetTextI18n")

    private fun checkImage(idImgQuestion: Int?) {
        val size = viewModel.questionImagesList.size - 1
        for (i in 1..size) {
            if (idImgQuestion == viewModel.questionImagesList[i].id) {
                val imgArray = viewModel.questionImagesList[i].image
                val bmp = BitmapFactory.decodeByteArray(imgArray, 0, imgArray.size)
                imgQuestion.isVisible = true
                imgQuestion.setImageBitmap(bmp)
                break
            } else {
                imgQuestion.isVisible = false
            }
        }
    }

    private fun alertEndTest() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, id -> timerTest(false) }
            .setNegativeButton("Нет") { dialog, id -> dialog.cancel() }
        val alert = dialogBuilder.create()
        alert.setTitle("Вы хотите завершить тест?")
        alert.show()
    }

    private fun timerTest(timerTest: Boolean) {
        Log.d("!!!TimeTestGet", timerTest.toString())
        var timeTest = viewModel.TOTAL_TIME
        val timer = object: CountDownTimer(timeTest.toLong(),2000) {
            @SuppressLint("DefaultLocale")
            override fun onTick(interval: Long) {
                tvTimer.text = String.format("%02d:%02d",
                    interval / 60000, interval % 60000 / 2000)
                timeTest -= 2000
            }
            override fun onFinish() {
                timerTest(false)
                Log.d("!!!Ti_onFinish", timerTest.toString())
            }
        }.start()
        if (!timerTest) {
            timer.cancel()
            Log.d("!!!TimerCancel", timerTest.toString())
            navController.navigate(R.id.resultTestFragment)
        }
    }
}