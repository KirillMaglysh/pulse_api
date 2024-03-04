package ru.prodcontest.db.reposetories

import jakarta.persistence.Tuple
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.prodcontest.db.entities.Friendship

interface FriendshipRepository : JpaRepository<Friendship, Long> {
    fun save(friendship: Friendship)

    fun existsByLoginAndFriend(login: String, friend: String): Boolean

    @Transactional
    fun deleteByLoginAndFriend(login: String, friend: String)

    @Query(
        value = "SELECT friend, added_at from friendships WHERE login=:log LIMIT :lim OFFSET :offs",
        nativeQuery = true
    )
    fun findAllByLogin(
        @Param("log") login: String,
        @Param("lim") lim: Int,
        @Param("offs") offs: Int
    ): MutableList<Tuple>
}