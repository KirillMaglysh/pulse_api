package ru.prodcontest.requests

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "Patch profile request")
class PatchProfileRequest(
    val countryCode: String?,
    val isPublic: Boolean?,
    val phone: String?,
    val image: String?
) : Serializable {
}