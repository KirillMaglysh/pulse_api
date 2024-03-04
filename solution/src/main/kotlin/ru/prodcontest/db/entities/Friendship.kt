package ru.prodcontest.db.entities

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.util.*

@Entity
@Table(name = "friendships")
class Friendship(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "login")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-]{1,30}\$")
    val login: String? = null,

    @Column(name = "friend")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-]{1,30}\$")
    val friend: String? = null,

    @Column(name = "added_at")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    val addedAt: Date? = null,
) {
    class FriendWoMe(
        @Column(name = "friend")
        val login: String,

        @Column(name = "added_at")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        val addedAt: Date
    )
}

data class FriendRequestParams(val limit: Int = 5, val offset: Int = 0)