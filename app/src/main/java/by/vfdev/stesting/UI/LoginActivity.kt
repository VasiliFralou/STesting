package by.vfdev.stesting.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var questionRecyclerview: RecyclerView
    private lateinit var questionArrayList : ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        questionRecyclerview = findViewById(R.id.rvQuestions)
        questionRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionRecyclerview.setHasFixedSize(true)
        questionArrayList = arrayListOf<Question>()
        getQuestionData()
    }

    private fun getQuestionData() {
        dbref = FirebaseDatabase.getInstance().getReference("Questions")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (questionSnapshot in snapshot.children) {
                        val question = questionSnapshot.getValue(Question::class.java)
                        questionArrayList.add(question!!)
                        Log.d("!!!", questionArrayList.toString())
                    }
                    questionRecyclerview.adapter = QuestionAdapter(questionArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }

}