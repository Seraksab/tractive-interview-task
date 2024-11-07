package com.tractive.pettracking.dto.request

import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.enum.TrackerType

data class CreatePetDto(
    val ownerId: Int,
    val petType: PetType,
    val trackerType: TrackerType
)
