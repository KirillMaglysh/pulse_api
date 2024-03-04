package ru.prodcontest.db.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "tokens")
class Token(
    @Id
    @Column(name = "token")
    @NotNull
    val token: String? = null
)