package by.vfdev.stesting.UI.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.vfdev.stesting.R
import by.vfdev.stesting.UI.StuffTestingActivity
import by.vfdev.stesting.ViewModel.QuestionViewModel
import by.vfdev.stesting.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var auth: FirebaseAuth

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(activity as StuffTestingActivity).get(QuestionViewModel::class.java)

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        val currentuser = auth.currentUser
        if(currentuser != null) {
            navController.navigate(R.id.mainFragment)
            viewModel.currentUser = auth.currentUser?.email.toString()
        }
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = etLEmailAuth.text.toString()
        val password = etLEmailPassword.text.toString()

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    navController.navigate(R.id.mainFragment)
                    viewModel.currentUser = email
                }
            }
            .addOnFailureListener {
                Toast.makeText(context,"Ошибка входа", Toast.LENGTH_LONG).show()
            }
    }
}