package by.vfdev.stesting.UI.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.vfdev.stesting.R
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_result_test.*

class ResultTestFragment : Fragment() {

    private lateinit var tvResult: TextView
    private lateinit var viewModel: QuestionViewModel

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_result_test, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        initViews(view)

        val result = viewModel.resultTest
        tvResult.text = "$result / 10"

        btnRestart.setOnClickListener { navController.navigate(R.id.testFragment) }
        btnGoToMenu.setOnClickListener { navController.navigate(R.id.mainFragment) }
    }

    private fun initViews(view: View) {
        tvResult = view.findViewById(R.id.tvResult)
    }
}