package com.tractive.pettracking.dto.response

import com.tractive.pettracking.enum.TrackerType
import java.util.UUID

data class TrackerDto(
    val id: UUID,
    val type: TrackerType
)
