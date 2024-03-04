package ru.prodcontest.controllers.auth

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.entities.User
import ru.prodcontest.db.exceptions.CreatingANewRowWithExistingKeyException
import ru.prodcontest.db.exceptions.WrongDBQueryArgumentException
import ru.prodcontest.services.UserService

@RestController
@RequestMapping("/api/auth/register")
class RegistrationController(@Autowired private val userService: UserService) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    @ResponseBody
    fun register(@RequestBody user: User): ResponseEntity<out Any> {
        logger.info { "Processing registration for user: $user" }

        return try {
            userService.create(user)
            ResponseEntity.status(201).body(hashMapOf(Pair("profile", userService.findWoPasswordByLogin(user.login!!))))
        } catch (e: WrongDBQueryArgumentException) {
            ResponseEntity.status(400).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: CreatingANewRowWithExistingKeyException) {
            ResponseEntity.status(409).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: Exception) {
            ResponseEntity.status(400).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}