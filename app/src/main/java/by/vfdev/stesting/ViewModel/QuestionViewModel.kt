package by.vfdev.stesting.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.vfdev.stesting.RemoteModel.Answer
import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.RemoteModel.QuestionImages
import by.vfdev.stesting.RemoteModel.UsersResult

class QuestionViewModel: ViewModel() {

    var TOTAL_TIME = 1*60*1000

    val questionList = mutableListOf<Question?>()
    val newList = mutableListOf<Answer?>()
    val userList = mutableListOf<UsersResult?>()
    val userScoresList = mutableListOf<UsersResult?>()
    lateinit var questionImagesList: List<QuestionImages>

    var currentUser: String = ""

    companion object {
        private var instance: QuestionViewModel? = null
        fun getInstance() =
            instance ?: synchronized(QuestionViewModel::class.java) {
                instance ?: QuestionViewModel().also { instance = it }
            }
    }
}

class MyFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(QuestionViewModel::class.java) -> QuestionViewModel.getInstance()
                else -> throw IllegalArgumentException("Unknown viewModel class $modelClass")
            }
        } as T
    companion object {
        private var instance: MyFactory? = null
        fun getInstance() =
            instance ?: synchronized(MyFactory::class.java) {
                instance ?: MyFactory().also { instance = it }
            }
    }
}