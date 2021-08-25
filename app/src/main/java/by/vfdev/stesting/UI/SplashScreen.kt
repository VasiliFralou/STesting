package by.vfdev.stesting.UI

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel
import com.google.firebase.database.*

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    lateinit var viewModel: QuestionViewModel
    private lateinit var dbref: DatabaseReference
    private var data: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)

        getQuestionData()

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // we used the postDelayed(Runnable, time) method to send a message with a delayed time.
        Handler().postDelayed({

            val intent = Intent(this, StuffTestingActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000) // 3000 is the delayed time in milliseconds.
    }

    private fun getQuestionData() {
        dbref = FirebaseDatabase.getInstance().getReference("Questions")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = true
                if (snapshot.exists()) {
                    for (questionSnapshot in snapshot.children) {
                        val question = questionSnapshot.getValue(Question::class.java)
                        viewModel.questionList.add(question!!)

                    }
                } else {
                    Log.d("!!!!!!!!!!!!!!!!!!!", "Ошибка!!!!")
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}