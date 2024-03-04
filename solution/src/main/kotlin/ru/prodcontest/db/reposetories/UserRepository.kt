package ru.prodcontest.db.reposetories

import org.springframework.data.jpa.repository.JpaRepository
import ru.prodcontest.db.entities.User

interface UserRepository : JpaRepository<User, String> {
    fun save(user: User)

    fun findProjectedWOPswByLogin(login: String): User.UserWOPsw?

    fun findCredentialsByLogin(login: String): User.UserCredentials?

    fun existsByLogin(login: String): Boolean
}
