package ru.prodcontest.services

import ru.prodcontest.db.entities.FriendRequestParams
import ru.prodcontest.db.entities.Friendship
import ru.prodcontest.requests.AddRemoveFriendRequest

interface FriendshipService {
    fun addFriend(login: String, addFriendRequest: AddRemoveFriendRequest)
    fun removeFriend(login: String, removeFriendRequest: AddRemoveFriendRequest)
    fun getFriends(login: String, friendsRequestParams: FriendRequestParams): MutableList<Friendship.FriendWoMe>
    fun isFriendOf(potentialFriend: String, user: String): Boolean
}