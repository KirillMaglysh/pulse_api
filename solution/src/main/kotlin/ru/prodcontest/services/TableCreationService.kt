package ru.prodcontest.services

interface TableCreationService {
    fun createUsersTableIfNotExists()
    fun createTokensTableIfNotExists()
    fun createPasswordUpdatesTableIfNotExists()
    fun createFriendshipsTableIfNotExists()
    fun createPostsTableIfNotExists()
    fun createReactionsTableIfNotExists()
}