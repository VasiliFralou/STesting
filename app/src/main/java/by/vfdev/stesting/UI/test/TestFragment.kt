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
import java.util.*
import kotlin.concurrent.timer
import android.os.CountDownTimer as CountDownTimer
import androidx.core.view.marginBottom as marginBottom

class TestFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    private var countDownTimer: CountDownTimer? = null
    private lateinit var question: Question
    private lateinit var tvTimer: TextView
    private lateinit var rbGroupQuestion: RadioGroup

    lateinit var navController: NavController

    // Default and the first question position
    private var positionQuestion: Int = 0
    private var timeLeft: Long = 0

    private val colorStateList = ColorStateList(arrayOf(
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_enabled)),
        intArrayOf(Color.WHITE , Color.WHITE)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        timeLeft = viewModel.TOTAL_TIME.toLong()

        val db = Room.databaseBuilder(requireActivity(),
            QuestionImagesDatabase::class.java, "STDB.db")
            .createFromAsset("STDB.db")
            .allowMainThreadQueries()
            .build()

        viewModel.questionImagesList = db.questionImagesDao().getAll().toMutableList()

        // Call function generation list question
        generationListQuestions()
        initViews(view)
        alertStartTest()
        startCountDown()

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

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun setNewQuestion(positionQuestion: Int) {
        rbGroupQuestion.removeAllViews()
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
        rightAns.text = "$positionList / 10"
        tvQuestion.text = viewModel.newList[positionQuestion]?.QuestionText

        checkImage(viewModel.newList[positionQuestion]?.Id)

        val random = info.shuffled()

        for (i in random.indices) {
            val newButton = RadioButton(requireActivity())
            if (random[i] == null)
                newButton.isVisible = false
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
        for (i in 0..size) {
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

    private fun alertStartTest() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setCancelable(false)
            .setMessage("\nКол-во вопросов: 10 шт.\nВремя теста: 1 минута\n")
            // Call function start timer
            .setPositiveButton("Да") { dialog, id ->
                countDownTimer?.start()
                setNewQuestion(positionQuestion)
            }
            .setNegativeButton("Нет") { dialog, id -> navController.navigate(R.id.mainFragment) }
        val alert = dialogBuilder.create()
        alert.setTitle("Начать тест?")
        alert.show()
    }

    private fun alertEndTest() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, id -> countDownTimer?.onFinish() }
            .setNegativeButton("Нет") { dialog, id -> dialog.cancel() }
        val alert = dialogBuilder.create()
        alert.setTitle("Вы хотите завершить тест?")
        alert.show()
    }

    private fun startCountDown() {
        countDownTimer = object: CountDownTimer(timeLeft,1000) {
            override fun onTick(interval: Long) {
                timeLeft = interval
                updateCountDown()
            }
            override fun onFinish() {
                updateCountDown()
                timeLeft = 0
                cancel()
                navController.navigate(R.id.resultTestFragment)
            }
        }
    }

    private fun updateCountDown() {
        val min = (timeLeft / 1000).toInt() / 60
        val sec = (timeLeft / 1000).toInt() % 60

        val timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        tvTimer.text = timeFormat
    }
}