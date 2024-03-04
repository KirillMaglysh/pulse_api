package ru.prodcontest.db.entities

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.annotation.Nonnull
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "posts")
class Post(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "content")
    @Nonnull
    val content: String? = null,

    @Column(name = "author")
    @Nonnull
    val author: String? = null,

    @Column(name = "tags")
    @Nonnull
    val tags: String? = null,

    @Column(name = "created_at")
    @Nonnull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val createdAt: Date? = null,

    @Column(name = "likes_count")
    @Nonnull
    var likesCount: Int = 0,

    @Column(name = "dislikes_count")
    @Nonnull
    var dislikesCount: Int = 0,
) : Serializable {
    class SerialPost(
        var id: Long? = null,
        val content: String? = null,
        val author: String? = null,
        val tags: Array<String>? = null,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        val createdAt: Date? = null,
        val likesCount: Int = 0,
        val dislikesCount: Int = 0
    ) : Serializable

    fun toSerial(): SerialPost {
        var tagsArr: Array<String>? = null
        if (tags != null) {
            tagsArr = tags.split(",").toTypedArray()
        }

        return SerialPost(
            id, content, author, tagsArr, createdAt, likesCount, dislikesCount
        )
    }
}