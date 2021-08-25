package by.vfdev.stesting.UI.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import by.vfdev.stesting.R
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.UI.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_stuff_testing.*
import kotlinx.android.synthetic.main.fragment_results.*

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
            (activity as StuffTestingActivity).openCloseNavigationDrawer()
        }
        pager2_container.adapter = ViewPagerAdapter(requireActivity(), fragmentList)
    }
}