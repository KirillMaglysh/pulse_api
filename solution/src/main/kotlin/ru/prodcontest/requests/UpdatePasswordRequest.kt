package ru.prodcontest.requests

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "Authentication request")
class UpdatePasswordRequest(
    val oldPassword: String?,
    val newPassword: String
) : Serializable