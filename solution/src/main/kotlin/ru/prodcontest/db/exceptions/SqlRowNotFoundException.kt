package ru.prodcontest.db.exceptions

class SqlRowNotFoundException(override val message: String) : Exception(message)
