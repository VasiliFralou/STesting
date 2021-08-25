package by.vfdev.stesting.UI.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import kotlinx.android.synthetic.main.fragment_global_results.*

class GlobalResultsFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_global_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userList.clear()

        val maxSize = viewModel.userScoresList.size - 1
        for (i in 0..maxSize) {
            viewModel.userList.add(
                    UsersResult(
                        viewModel.userScoresList[i]?.user,
                        viewModel.userScoresList[i]?.scores,
                        viewModel.userScoresList[i]?.date)
                )
            }
        rvGlobalResultList.layoutManager = LinearLayoutManager(activity as StuffTestingActivity)
        rvGlobalResultList.adapter = GlobalResultAdapter(viewModel.userList, this)
    }
}