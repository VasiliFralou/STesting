package by.vfdev.stesting.RemoteModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "QuestionImages")
data class QuestionImages(
    @PrimaryKey val id: Int,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionImages

        if (id != other.id) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + image.contentHashCode()
        return result
    }
}