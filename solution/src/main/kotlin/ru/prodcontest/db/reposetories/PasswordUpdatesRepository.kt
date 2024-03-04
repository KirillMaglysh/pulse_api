package ru.prodcontest.db.reposetories

import org.springframework.data.jpa.repository.JpaRepository
import ru.prodcontest.db.entities.PasswordUpdate

interface PasswordUpdatesRepository : JpaRepository<PasswordUpdate, String> {
    fun save(passwordUpdate: PasswordUpdate)
}