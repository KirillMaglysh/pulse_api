package ru.prodcontest.db.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "password_updates")
class PasswordUpdate(
    @Id
    @Column(name = "login")
    @NotNull
    val login: String? = null,

    @Column(name = "updated")
    @NotNull
    val updated: Long? = null
)