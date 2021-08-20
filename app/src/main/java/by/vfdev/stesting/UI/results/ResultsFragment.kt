package by.vfdev.stesting.UI.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.vfdev.stesting.R
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.UI.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.android.synthetic.main.fragment_test.*

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            MyResultsFragment(),
            GlobalResultsFragment()
        )
        btnFrNavMenu.setOnClickListener{ v ->
            (activity as StuffTestingActivity).openCloseNavigationDrawer(v)
        }
        pager2_container.adapter = ViewPagerAdapter(requireActivity(), fragmentList)
    }
}