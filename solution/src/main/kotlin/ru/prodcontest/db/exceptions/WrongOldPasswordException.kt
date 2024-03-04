package ru.prodcontest.db.exceptions

class WrongOldPasswordException(override val message: String) : Exception(message) {
}