package by.vfdev.stesting.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.vfdev.stesting.R
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.activity_stuff_testing.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.header.*

class MainFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        tvWelcome.text = viewModel.currentUser

        btnStartTest.setOnClickListener {
            navController.navigate(R.id.testFragment)
        }
        btnResult.setOnClickListener {
            navController.navigate(R.id.resultsFragment)
        }
        btnSetting.setOnClickListener {
            navController.navigate(R.id.settingFragment)
        }
    }
}