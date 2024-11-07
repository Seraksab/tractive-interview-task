package com.tractive.pettracking.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull


@Schema(title = "UpdateTrackerData")
data class UpdateTrackerDataDto(

    @field:Schema(
        description = "Whether the tracker is in the zone",
        example = "true",
        nullable = false,
    )
    @field:NotNull
    val inZone: Boolean,

    @field:Schema(
        description = "Whether the tracker was lost",
        example = "true",
        nullable = true,
    )
    val lostTracker: Boolean?
)
