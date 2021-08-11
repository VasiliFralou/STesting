package by.vfdev.stesting.UI

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.vfdev.stesting.R
import kotlinx.android.synthetic.main.item_page.view.*

class ViewPagerAdapter(fa: FragmentActivity, private val fragments: ArrayList<Fragment>): FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}