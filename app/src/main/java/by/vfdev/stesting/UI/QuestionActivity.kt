package by.vfdev.stesting.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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

    var timeTest = Common.TOTAL_TIME

    lateinit var adapter : GridAnswerAdapter
    lateinit var countDownTimer: CountDownTimer

    private lateinit var dbref: DatabaseReference
    private lateinit var questionRecyclerview: RecyclerView
    private lateinit var questionList : ArrayList<Question>

    lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        questionRecyclerview = findViewById(R.id.grid_answer)
        questionRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionRecyclerview.setHasFixedSize(true)

        // viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        // val vm = ViewModelProvider(this).get(QuestionViewModel::class.java)

        viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)

        questionList = arrayListOf<Question>()

        getQuestionData()
        countTimer()
        genItems()

        grid_answer.setHasFixedSize(true)
        if(questionList.size > 0)
            grid_answer.layoutManager = GridLayoutManager(this,
                if(questionList.size > 5) questionList.size / 2 else questionList.size)
        adapter = GridAnswerAdapter(this, Common.answerSheetList)
        grid_answer.adapter = adapter

        // Generation Fragment List
        genFragmentList()

        val fragmentAdapter = FragmentAdapter(supportFragmentManager, this, Common.fragmentList)
        view_pager.offscreenPageLimit = questionList.size

        // Bind question to View Pager
        view_pager.adapter = fragmentAdapter

        sliding_tabs.setupWithViewPager(view_pager)
    }

    private fun genFragmentList() {
        for(i in questionList.indices) {
            val bundle = Bundle()
            bundle.putInt("index", i)
            val fragment = QuestionFragment()
            fragment.arguments = bundle

            Common.fragmentList.add(fragment)
        }
    }

    private fun genItems() {
        for(i in questionList.indices) {

            // No answer for all question
            Common.answerSheetList.add(CurrentQuestion(i, Common.ANSWER_TYPE.NO_ANSWER))
        }
    }

    private fun countTimer() {
        countDownTimer = object: CountDownTimer(Common.TOTAL_TIME.toLong(),1000) {
            override fun onTick(interval: Long) {
                txt_timer.text = (java.lang.String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMillis(interval),
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
                        questionList.add(question!!)
                        Log.d("!!!", questionList.toString())
                    }
                    questionRecyclerview.adapter = QuestionAdapter(questionList)
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }
}