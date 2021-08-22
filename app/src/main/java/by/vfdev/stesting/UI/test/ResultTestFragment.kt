package by.vfdev.stesting.UI.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_result_test.*
import java.text.SimpleDateFormat
import java.util.*

class ResultTestFragment : Fragment() {

    private lateinit var tvResult: TextView
    private lateinit var viewModel: QuestionViewModel

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Results")

        return inflater.inflate(R.layout.fragment_result_test, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        initViews(view)

        val size = viewModel.newList.size - 1
        var correctAnswers = 0

        for (i in 0..size) {
            if (viewModel.newList[i]?.CurrentAnswer == viewModel.newList[i]?.CorrectAnswer) {
                correctAnswers++
            }
        }

        val user = viewModel.currentUser
        val scores = correctAnswers
        tvResult.text = "$scores / 20"
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val date = sdf.format(Date())

        val model = UsersResult(user, scores, date)
        val id = reference.push().key

        reference.child(id!!).setValue(model)

        btnRestart.setOnClickListener { navController.navigate(R.id.testFragment) }
        btnGoToMenu.setOnClickListener { navController.navigate(R.id.mainFragment) }
        btnListAnswer.setOnClickListener { navController.navigate(R.id.userAnswerListFragment) }
    }

    private fun initViews(view: View) {
        tvResult = view.findViewById(R.id.tvResult)
    }
}