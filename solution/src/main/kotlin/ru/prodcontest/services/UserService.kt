package ru.prodcontest.services

import ru.prodcontest.db.entities.User
import ru.prodcontest.requests.PatchProfileRequest
import ru.prodcontest.requests.SignInRequest
import ru.prodcontest.requests.UpdatePasswordRequest

interface UserService {
    fun create(user: User)
    fun findWoPasswordByLogin(login: String): User.UserWOPsw
    fun checkCredentials(signInRequest: SignInRequest): Boolean
    fun patch(login: String, patchProfileRequest: PatchProfileRequest)
    fun updatePassword(login: String, updatePasswordRequest: UpdatePasswordRequest)
    fun exists(login: String): Boolean
    fun getProfile(client: String, login: String): User.UserWOPsw
}
