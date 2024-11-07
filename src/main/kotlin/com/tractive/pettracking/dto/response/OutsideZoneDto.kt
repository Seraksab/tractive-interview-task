package com.tractive.pettracking.dto.response

import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.enum.TrackerType

data class OutsideZoneDto(
    val petType: PetType,
    val trackerType: TrackerType,
    val count: Long
)