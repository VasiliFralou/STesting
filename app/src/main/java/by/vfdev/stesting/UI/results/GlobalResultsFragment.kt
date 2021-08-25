package by.vfdev.stesting.UI.results

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.vfdev.stesting.R
import by.vfdev.stesting.UI.StuffTestingActivity
import kotlinx.android.synthetic.main.fragment_test.*

class GlobalResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_global_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kitty = "murzik@gmail.com"
        kitty.substringBefore("@") // murzik
        Log.d("!!!Mur", kitty.substringBefore("@"))
    }
}