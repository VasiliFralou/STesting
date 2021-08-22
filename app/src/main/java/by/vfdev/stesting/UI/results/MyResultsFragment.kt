package by.vfdev.stesting.UI.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_my_results.*


class MyResultsFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_my_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userList.clear()

        val maxSize = viewModel.userScoresList.size - 1
        for (i in 0..maxSize) {
            if (viewModel.userScoresList[i]?.user == viewModel.currentUser) {
                viewModel.userList.add(
                    UsersResult(
                    viewModel.userScoresList[i]?.user,
                    viewModel.userScoresList[i]?.scores,
                    viewModel.userScoresList[i]?.date))
            }
        }
        rvResultList.layoutManager = LinearLayoutManager(activity as StuffTestingActivity)
        rvResultList.adapter = ResultAdapter(viewModel.userList, this)
    }
}