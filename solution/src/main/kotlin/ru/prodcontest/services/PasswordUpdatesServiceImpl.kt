package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.PasswordUpdate
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.reposetories.PasswordUpdatesRepository

@Service
class PasswordUpdatesServiceImpl(@Autowired private val passwordUpdatesRepo: PasswordUpdatesRepository) :
    PasswordUpdatesService {
    override fun save(passwordUpdate: PasswordUpdate) {
        passwordUpdatesRepo.save(passwordUpdate)
    }

    override fun getLastUpdate(login: String): Long {
        val passwordUpdate = passwordUpdatesRepo.findByIdOrNull(login) ?: throw ApiException("Invalid login")
        return passwordUpdate.updated!!
    }

}