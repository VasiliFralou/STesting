package by.vfdev.stesting.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.Common.Common
import by.vfdev.stesting.Common.CurrentQuestion
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_question.*
import java.util.concurrent.TimeUnit

class QuestionActivity : AppCompatActivity() {

    lateinit var adapter : GridAnswerAdapter
    lateinit var countDownTimer: CountDownTimer

    private lateinit var dbref: DatabaseReference
    private lateinit var questionRecyclerview: RecyclerView

    lateinit var viewModel: QuestionViewModel
    var timeTest = Common.TOTAL_TIME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        questionRecyclerview = findViewById(R.id.gridAnswer)
        questionRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionRecyclerview.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)

        getQuestionData()
        countTimer()
        genItems()

        gridAnswer.setHasFixedSize(true)
        if(viewModel.questionList.size > 0)
            gridAnswer.layoutManager = GridLayoutManager(this,
                if(viewModel.questionList.size > 5) viewModel.questionList.size / 2 else viewModel.questionList.size)
        adapter = GridAnswerAdapter(this, Common.answerSheetList)
        gridAnswer.adapter = adapter

        // Generation Fragment List
        genFragmentList()

        val fragmentAdapter = FragmentAdapter(supportFragmentManager, this, Common.fragmentList)
        view_pager.offscreenPageLimit = viewModel.questionList.size

        // Bind question to View Pager
        view_pager.adapter = fragmentAdapter

        sliding_tabs.setupWithViewPager(view_pager)
    }

    private fun genFragmentList() {
        for(i in viewModel.questionList.indices) {
            val bundle = Bundle()
            bundle.putInt("index", i)
            val fragment = QuestionFragment()
            fragment.arguments = bundle

            Common.fragmentList.add(fragment)
        }
    }

    private fun genItems() {
        for(i in viewModel.questionList.indices) {

            // No answer for all question
            Common.answerSheetList.add(CurrentQuestion(i, Common.ANSWER_TYPE.NO_ANSWER))
        }
    }

    private fun countTimer() {
        countDownTimer = object: CountDownTimer(Common.TOTAL_TIME.toLong(),1000) {
            override fun onTick(interval: Long) {
                tvTimer.text = (java.lang.String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMillis(interval),
                TimeUnit.MILLISECONDS.toSeconds(interval) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(interval))))
                timeTest -= 1000
            }
            override fun onFinish() {
                finishTest()
            }
        }
    }

    private fun finishTest() {

    }

    private fun getQuestionData() {
        dbref = FirebaseDatabase.getInstance().getReference("Questions")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (questionSnapshot in snapshot.children) {
                        val question = questionSnapshot.getValue(Question::class.java)
                        viewModel.questionList.add(question!!)
                        Log.d("!!!VM", viewModel.questionList.toString())
                    }
                    questionRecyclerview.adapter = QuestionAdapter(viewModel.questionList as ArrayList<Question>)
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }
}