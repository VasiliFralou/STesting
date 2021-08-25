package by.vfdev.stesting.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.vfdev.stesting.R
import by.vfdev.stesting.RemoteModel.UsersResult
import by.vfdev.stesting.ViewModel.MyFactory
import by.vfdev.stesting.ViewModel.QuestionViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_stuff_testing.*

class StuffTestingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var viewModel: QuestionViewModel
    lateinit var navController: NavController
    private lateinit var dbref: DatabaseReference
    private var data: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stuff_testing)

        viewModel = ViewModelProvider(this, MyFactory.getInstance()).get(QuestionViewModel::class.java)

        getUsersResultData()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuMain -> {
                    navController.navigate(R.id.mainFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.menuStartTest -> {
                    navController.navigate(R.id.testFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.menuResult -> {
                    navController.navigate(R.id.resultsFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.menuSetting -> {
                    navController.navigate(R.id.settingFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {
                if (navController.currentDestination?.id == R.id.loginFragment
                    || navController.currentDestination?.id == R.id.mainFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
            }
        })
    }

    private fun getUsersResultData() {
        dbref = FirebaseDatabase.getInstance().getReference("Results")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = true
                if (snapshot.exists()) {
                    viewModel.userScoresList.clear()
                    for (scoresSnapshot in snapshot.children) {
                        val scores = scoresSnapshot.getValue(UsersResult::class.java)
                            viewModel.userScoresList.add(scores!!)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun openCloseNavigationDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        if (navController.currentDestination?.id == R.id.testFragment
            || navController.currentDestination?.id == R.id.resultTestFragment) {
            return
        }
        super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}