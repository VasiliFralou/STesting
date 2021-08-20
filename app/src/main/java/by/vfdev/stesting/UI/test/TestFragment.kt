package by.vfdev.stesting.UI.test

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.room.Room
import by.vfdev.stesting.LocalModel.QuestionImagesDatabase
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.CurrentQuestion
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.RemoteModel.QuestionImages
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_test.*


class TestFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    private lateinit var question: Question
    private lateinit var currentAnswer: CurrentQuestion
    private lateinit var tvTimer: TextView
    private lateinit var rbGroupQuestion: RadioGroup
    private lateinit var viewModel: QuestionViewModel
    private lateinit var answerChecked: String

    lateinit var navController: NavController
    lateinit var questionImagesList: List<QuestionImages>

    // Default and the first question position
    private var currentQuestionPosition: Int = 1
    private var correctAnswers: Int = 0
    private var rnds: Int = 0
    private var maxSize: Int = 0

    private val colorStateList = ColorStateList(arrayOf(
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(android.R.attr.state_enabled)),
        intArrayOf(Color.WHITE , Color.WHITE)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)
        viewModel.resultTest = 0

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

        questionImagesList = db.questionImagesDao().getAll()
        maxSize = viewModel.questionList.size - 1

        initViews(view)
        getQuestion()
        timerTest()

        btnNext.setOnClickListener {
            nextQuestion(view)
        }
        btnBack.setOnClickListener {
            // backQuestion(view)
        }
        btnSkip.setOnClickListener {
            // skipQuestion(view)
        }
        btnNavMenu.setOnClickListener{ v ->
            (activity as StuffTestingActivity).openCloseNavigationDrawer(v)
        }
        rbGroupQuestion.setOnCheckedChangeListener { radioGroup, id ->
            val rb = view.findViewById<RadioButton>(id)
            if (rb != null)
                answerChecked = rb.text.toString()
        }
    }

    private fun getQuestion() {
        rbGroupQuestion.clearCheck()
        maxSize = viewModel.questionList.size - 1
        Log.d("!!!maxSize", maxSize.toString())
        rnds = (0..maxSize).random()
        question = viewModel.questionList[rnds]!!
        setQuestion()
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun setQuestion() {

        val info = arrayListOf(question.AnswerA,
            question.AnswerB, question.AnswerC,
            question.AnswerD, question.AnswerE)

        progressBar.progress = currentQuestionPosition
        rightAns.text = "$currentQuestionPosition / 10"
        tvQuestion.text = question.QuestionText

        checkImage()

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

    @SuppressLint("SetTextI18n")
    private fun nextQuestion(view: View) {
        when {
            currentQuestionPosition < 10 -> {
                currentQuestionPosition++
                checkAnswer()
                getQuestion()
            }
            currentQuestionPosition == 10 -> {
                btnNext.text = "FINISH"
                alertEndTest()
            }
        }
    }

    private fun checkAnswer() {
        if (question.CorrectAnswer == answerChecked) {
            correctAnswers++
        }
    }

    private fun checkImage() {
        for (i in 1..questionImagesList.size)
        if (question.Id == questionImagesList[i-1].id) {
            val imgArray = questionImagesList[i-1].image
            val bmp = BitmapFactory.decodeByteArray(imgArray, 0,imgArray.size)
            imgQuestion.isVisible = true
            imgQuestion.setImageBitmap(bmp)
        } else {
            imgQuestion.isVisible = false
        }
    }

    private fun finishTest() {
        if (question.CorrectAnswer == answerChecked) {
            correctAnswers++
        }
        viewModel.resultTest = correctAnswers
        navController.navigate(R.id.resultTestFragment)
    }

    private fun alertEndTest() {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setCancelable(false)
            .setPositiveButton("Да") { dialog, id -> finishTest() }
            .setNegativeButton("Нет") { dialog, id -> dialog.cancel() }
        val alert = dialogBuilder.create()
        alert.setTitle("Вы хотите завершить тест?")
        alert.show()
    }

    private fun initViews(view: View) {
        tvTimer = view.findViewById(R.id.tvTimer)
        rbGroupQuestion = view.findViewById(R.id.rbGroupQuestion)
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