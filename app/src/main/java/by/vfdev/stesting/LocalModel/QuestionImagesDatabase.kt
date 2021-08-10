package by.vfdev.stesting.LocalModel

import androidx.room.Database
import androidx.room.RoomDatabase
import by.vfdev.stesting.RemoteModel.QuestionImages

@Database(entities = [QuestionImages::class], version = 1)
abstract class QuestionImagesDatabase: RoomDatabase() {
    abstract fun questionImagesDao(): iQuestionImagesDao
}