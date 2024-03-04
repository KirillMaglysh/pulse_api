package ru.prodcontest.db.entities

import jakarta.annotation.Nonnull
import jakarta.persistence.*

@Entity
@Table(name = "reactions")
class Reaction(
    @Id()
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "login")
    @Nonnull
    val login: String? = null,

    @Column(name = "postId")
    @Nonnull
    var postId: Long? = null,

    @Column(name = "is_like")
    @Nonnull
    var isLike: Boolean? = null
)