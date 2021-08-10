package by.vfdev.stesting.LocalModel

import androidx.room.Dao
import androidx.room.Query
import by.vfdev.stesting.RemoteModel.QuestionImages

@Dao
interface iQuestionImagesDao {
    @Query("SELECT * FROM QuestionImages")
    fun getAll(): List<QuestionImages>
}