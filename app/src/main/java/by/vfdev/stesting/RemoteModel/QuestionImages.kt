package by.vfdev.stesting.RemoteModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "QuestionImages")
data class QuestionImages(
    @PrimaryKey val id: Int,
    val image: ByteArray
)