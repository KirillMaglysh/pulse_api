package ru.prodcontest.db.exceptions

class WrongDBQueryArgumentException(override val message: String) : ApiException(message)