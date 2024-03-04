package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.PasswordUpdate
import ru.prodcontest.db.entities.User
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.exceptions.CreatingANewRowWithExistingKeyException
import ru.prodcontest.db.exceptions.WrongDBQueryArgumentException
import ru.prodcontest.db.exceptions.WrongOldPasswordException
import ru.prodcontest.db.reposetories.FriendshipRepository
import ru.prodcontest.db.reposetories.UserRepository
import ru.prodcontest.requests.PatchProfileRequest
import ru.prodcontest.requests.SignInRequest
import ru.prodcontest.requests.UpdatePasswordRequest


@Service
class UserServiceImpl(
    @Autowired private val userRepo: UserRepository,
    @Autowired private val passwordUpdatesService: PasswordUpdatesService,
//    @Autowired private val friendshipService: FriendshipService,
    @Autowired private val friendshipRepo: FriendshipRepository,
    @Autowired private val tableCreationService: TableCreationService,
    @Autowired private val encoder: PasswordEncoder,
) : UserService {
    private val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,100}\$")

    init {
        tableCreationService.createUsersTableIfNotExists()
        tableCreationService.createPasswordUpdatesTableIfNotExists()
        // TODO remove when solve cyclic
        tableCreationService.createFriendshipsTableIfNotExists()
    }

    override fun create(user: User) {
        checkPassword(user.password)
        user.password = encryptPassword(user.password!!)

        if (userRepo.existsByLogin(user.login!!)) {
            throw CreatingANewRowWithExistingKeyException("Provided login is already used")
        }

        userRepo.save(user)
        passwordUpdatesService.save(PasswordUpdate(user.login, System.currentTimeMillis()))
    }

    override fun findWoPasswordByLogin(login: String): User.UserWOPsw {
        return userRepo.findProjectedWOPswByLogin(login)
            ?: throw ApiException("No user found with provided login found")
    }

    override fun checkCredentials(signInRequest: SignInRequest): Boolean {
        val credentials = userRepo.findCredentialsByLogin(signInRequest.login!!) ?: return false
        return encoder.matches(signInRequest.password, credentials.password)
    }

    override fun patch(login: String, patchProfileRequest: PatchProfileRequest) {
        val user = userRepo.findByIdOrNull(login) ?: throw WrongDBQueryArgumentException("Invalid login")

        if (patchProfileRequest.countryCode != null) {
            user.countryCode = patchProfileRequest.countryCode
        }

        if (patchProfileRequest.isPublic != null) {
            user.isPublic = patchProfileRequest.isPublic
        }

        if (patchProfileRequest.phone != null) {
            user.phone = patchProfileRequest.phone
        }

        if (patchProfileRequest.image != null) {
            user.image = patchProfileRequest.image
        }

        try {
            userRepo.save(user)
        } catch (e: Exception) {
            throw WrongDBQueryArgumentException("Incorrect patch request")
        }
    }

    override fun updatePassword(login: String, updatePasswordRequest: UpdatePasswordRequest) {
        checkPassword(updatePasswordRequest.oldPassword)
        checkPassword(updatePasswordRequest.newPassword)

        val user = userRepo.findByIdOrNull(login) ?: throw ApiException("Invalid logiin")
        if (!encoder.matches(updatePasswordRequest.oldPassword, user.password)) {
            throw WrongOldPasswordException("Wrong old password")
        }

        user.password = encryptPassword(updatePasswordRequest.newPassword)
        userRepo.save(user)
        passwordUpdatesService.save(PasswordUpdate(user.login, System.currentTimeMillis()))
    }

    override fun exists(login: String): Boolean {
        return userRepo.existsByLogin(login)
    }

    override fun getProfile(client: String, login: String): User.UserWOPsw {
        if (!userRepo.existsByLogin(login)) {
            throw ApiException("No user with provided login")
        }

        val user = userRepo.findProjectedWOPswByLogin(login)

        if (!hasAccess(client, user!!)) {
            throw ApiException("You have no access to this user's profile")
        }

        return user
    }

    private fun hasAccess(client: String, user: User.UserWOPsw): Boolean {
        return client == user.login || user.isPublic || isFriendOf(client, user.login)
    }

    //TODO remove!!! заглушка для циклических компонентов
    private fun isFriendOf(potentialFriend: String, user: String): Boolean {
        return friendshipRepo.existsByLoginAndFriend(user, potentialFriend)
    }

    private fun encryptPassword(password: String): String? = encoder.encode(password)

    private fun checkPassword(password: String?) {
        if (password == null) {
            throw WrongDBQueryArgumentException("Password can't be null")
        }

        if (!passwordRegex.matches(password)) {
            throw WrongDBQueryArgumentException("Password is too weak")
        }
    }
}
