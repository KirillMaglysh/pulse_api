package ru.prodcontest.requests

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "New post request")
class NewPostRequest(
    val content: String?,
    val tags: List<String>?
) : Serializable