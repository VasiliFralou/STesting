package by.vfdev.stesting.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import by.vfdev.stesting.R
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel

class StuffTestingActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    lateinit var viewModel: QuestionViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)
        Log.d("!!!VM_LIST", viewModel.questionList.toString())

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val checkedRadioButton = group?.findViewById(group.checkedRadioButtonId) as? RadioButton
        checkedRadioButton?.let {
            if (checkedRadioButton.isChecked) {

                Log.d("!!!RB", "+- job")
                //answerChecked = checkedRadioButton.text as String
                // Toast.makeText(applicationContext, answerChecked, Toast.LENGTH_LONG).show()
            }
        }
    }
}