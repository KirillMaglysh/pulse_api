package ru.prodcontest.requests

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "Friend adding request")
class AddRemoveFriendRequest(
    @Schema(description = "Friend's login", example = "mkprime")
    val login: String?
) : Serializable