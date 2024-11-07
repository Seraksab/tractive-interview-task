package com.tractive.pettracking.dto.request

import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.enum.TrackerType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

@Schema(title = "CreatePet")
data class CreatePetDto(

    @field:Schema(
        description = "The ID of the owner",
        example = "1",
        nullable = false,
    )
    @field:NotNull
    @field:Positive
    val ownerId: Int,

    @field:Schema(
        description = "The type of the pet",
        example = "DOG",
        nullable = false,
    )
    @field:NotNull
    val petType: PetType,

    @field:Schema(
        description = "The type of the tracker",
        example = "DOG_SMALL",
        nullable = false,
    )
    @field:NotNull
    val trackerType: TrackerType
)
