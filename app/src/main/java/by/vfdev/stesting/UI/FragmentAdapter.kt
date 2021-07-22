package by.vfdev.stesting.UI

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.lang.StringBuilder

class FragmentAdapter(fragmentManager: FragmentManager, var context: Context,
                      var fragmentList: List<QuestionFragment>): FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return StringBuilder().append(position + 1).toString()
    }
}