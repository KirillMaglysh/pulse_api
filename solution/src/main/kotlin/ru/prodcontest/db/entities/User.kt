package ru.prodcontest.db.entities

import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.annotation.Nonnull
import jakarta.annotation.Nullable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Pattern
import java.io.Serializable

@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
class User : Serializable {
    @Id
    @Column(name = "login", unique = true)
    @Nonnull
    @Pattern(regexp = "^[a-zA-Z0-9-]{1,30}\$")
    val login: String? = null

    @Column(name = "email")
    @Nonnull
    @Pattern(regexp = "^.{1,50}\$")
    val email: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "countrycode")
    @Nonnull
    @Pattern(regexp = "^[a-zA-Z]{2}\$")
    var countryCode: String? = null

    @Column(name = "ispublic")
    @Nonnull
    var isPublic: Boolean? = null

    @Column(name = "phone", nullable = true)
    @Nullable
    @Pattern(regexp = "^\\+\\d{1,19}\$")
    var phone: String? = null

    @Column(name = "image", nullable = true)
    @Nullable
    @Pattern(regexp = "^.{1,200}\$")
    var image: String? = null

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class UserWOPsw(
        val login: String,
        val email: String,
        val countryCode: String,
        val isPublic: Boolean,
        val phone: String?,
        val image: String?
    ) : Serializable

    class UserCredentials(
        val login: String,
        val password: String
    )
}
