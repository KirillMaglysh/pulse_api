package ru.prodcontest.db.exceptions

class CreatingANewRowWithExistingKeyException(override val message: String) : Exception(message)