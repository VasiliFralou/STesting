package by.vfdev.stesting.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import by.vfdev.stesting.R
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)
    }

    fun startTest(view: View) {
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }
}