package ru.prodcontest.services

import ru.prodcontest.db.entities.PasswordUpdate

interface PasswordUpdatesService {
    fun save(passwordUpdate: PasswordUpdate)

    fun getLastUpdate(login: String): Long
}