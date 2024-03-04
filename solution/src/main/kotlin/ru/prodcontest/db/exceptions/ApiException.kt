package ru.prodcontest.db.exceptions

open class ApiException(override val message: String) : Exception(message) {
}