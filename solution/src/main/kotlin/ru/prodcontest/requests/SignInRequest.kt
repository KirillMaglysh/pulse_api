package ru.prodcontest.requests

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "Authentication request")
class SignInRequest(
    @Schema(description = "User's login", example = "mkprime")
    val login: String?,

    @Schema(description = "User's password", example = "p@Ssword123")
    val password: String
) : Serializable {
    override fun toString(): String {
        return "{login = $login password=$password}"
    }
}