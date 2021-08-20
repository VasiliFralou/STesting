package by.vfdev.stesting.UI.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_my_results.*
import kotlinx.android.synthetic.main.item_layout.*


class MyResultsFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel
    lateinit var navController: NavController

    private lateinit var dbref: DatabaseReference
    private var data: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_my_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        recyclerView.layoutManager = LinearLayoutManager(activity as StuffTestingActivity)
        recyclerView.adapter = ResultAdapter(viewModel.userScoresList, this)
    }
}