package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.FriendRequestParams
import ru.prodcontest.db.entities.Friendship
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.reposetories.FriendshipRepository
import ru.prodcontest.requests.AddRemoveFriendRequest
import java.util.*

@Service
class FriendshipServiceImpl(
    @Autowired private val friendshipRepo: FriendshipRepository,
    @Autowired private val userService: UserService,
    @Autowired private val tableCreationService: TableCreationService
) : FriendshipService {

    init {
        tableCreationService.createFriendshipsTableIfNotExists()
    }

    override fun addFriend(login: String, addFriendRequest: AddRemoveFriendRequest) {
        val friend = addFriendRequest.login ?: throw ApiException("Friend login can't be null")
        if (!userService.exists(friend)) {
            throw ApiException("User with provided login not found")
        }

        if (login == friend) {
            return
        }

        if (friendshipRepo.existsByLoginAndFriend(login, friend)) {
            return
        }

        friendshipRepo.save(Friendship(id = null, login, friend, Date()))
    }

    override fun removeFriend(login: String, removeFriendRequest: AddRemoveFriendRequest) {
        val friend = removeFriendRequest.login ?: return
        friendshipRepo.deleteByLoginAndFriend(login, friend)
    }

    override fun getFriends(
        login: String,
        friendsRequestParams: FriendRequestParams
    ): MutableList<Friendship.FriendWoMe> {
        val friendsRaw =
            friendshipRepo.findAllByLogin(login, friendsRequestParams.limit, friendsRequestParams.offset)
        val friends = ArrayList<Friendship.FriendWoMe>()
        friendsRaw.forEach {
            friends.add(Friendship.FriendWoMe(it[0] as String, it[1] as Date))
        }
        return friends
    }

    override fun isFriendOf(potentialFriend: String, user: String): Boolean {
        return friendshipRepo.existsByLoginAndFriend(user, potentialFriend)
    }
}
