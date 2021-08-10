package by.vfdev.stesting.UI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.BitmapFactory
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
import androidx.room.Room
import by.vfdev.stesting.LocalModel.QuestionImagesDatabase
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.RemoteModel.QuestionImages
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_test.*


class TestFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    private lateinit var question: Question
    private lateinit var tvTimer: TextView
    private lateinit var rbGroupQuestion: RadioGroup
    private lateinit var viewModel: QuestionViewModel
    private lateinit var answerChecked: String

    lateinit var navController: NavController
    lateinit var questionImagesList: List<QuestionImages>

    // Default and the first question position
    private var currentPosition: Int = 0
    private var correctAnswers: Int = 0
    private var rnds: Int = 0
    private var maxSize: Int = 0
    private var position = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        maxSize = viewModel.questionList.size - 1

        initViews(view)
        setQuestion()
        timerTest()

        // ?????????????????????????????????????????
//        val db = Room.databaseBuilder(requireActivity(),
//            QuestionImagesDatabase::class.java, "STestingDB.db")
//            .createFromAsset("STesting.db")
//            .allowMainThreadQueries()
//            .build()
//
//        questionImagesList = db.questionImagesDao().getAll()
//
//        showImage()

        btnNext.setOnClickListener {
            nextQuestion()
        }
        rbGroupQuestion.setOnCheckedChangeListener { radioGroup, id ->
            val rb = view.findViewById<RadioButton>(id)
            if (rb != null)
                answerChecked = rb.text.toString()
        }
    }

    fun showImage() {
        val imageArray = questionImagesList[position].image
        val bmp = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size)
        imgQuestion.setImageBitmap(bmp)
    }

    private fun initViews(view: View) {
        tvTimer = view.findViewById(R.id.tvTimer)
        rbGroupQuestion = view.findViewById(R.id.rbGroupQuestion)
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        currentPosition++
        rbGroupQuestion.clearCheck()

        rnds = (0..maxSize).random()
        progressBar.progress = currentPosition
        rightAns.text = "$currentPosition / 10"

        question = viewModel.questionList[rnds]!!

        when {
            currentPosition == 10 -> {
                btnNext.text = "FINISH"
            }
            currentPosition > 10 -> {
                Log.d("!!!Result: ", "$correctAnswers / 10")
            }
        }

        tvQuestion.text = question.QuestionText

//        if (question.Id == questionImagesList.) {
//        } else {
//            val imgResId = question.QuestionImage
//            var resId = imgResId!!.toInt()
//            imgQuestion.setImageResource(resId)
//        }

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
        when {
            currentPosition == 10 -> {
                alertEndTest(currentPosition)
            }
            currentPosition < 10 -> {
                if (question.CorrectAnswer == answerChecked) {
                    correctAnswers++
                    Log.d("!!!", "+")
                    viewModel.resultTest = correctAnswers
                    setQuestion()
                }
            }
        }
    }

    private fun finishTest() {
        if (question.CorrectAnswer == answerChecked) {
            correctAnswers++
        }
        navController.navigate(R.id.resultTestFragment)
    }

    private fun alertEndTest(currentPosition: Int) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Да", DialogInterface.OnClickListener {
                    dialog, id ->
                if (currentPosition == 10)
                    finishTest()
            })
            // negative button text and action
            .setNegativeButton("Нет", DialogInterface.OnClickListener {
                    dialog, id ->
                dialog.cancel()
            })
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Вы хотите завершить тест?")
        // show alert dialog
        alert.show()
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