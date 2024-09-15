import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.bookspot.Model.PublishedDate

@Entity(tableName = "books")
data class Book(
    val authors: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    @PrimaryKey val isbn: String,
    val longDescription: String? = null,
    val pageCount: Int = 0,
    val publishedDate: PublishedDate? = null,
    val shortDescription: String? = null,
    val status: String? = null,
    val thumbnailUrl: String? = null,
    val title: String? = null
)