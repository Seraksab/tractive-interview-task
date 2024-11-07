package com.tractive.pettracking.dto.response

import com.tractive.pettracking.enum.PetType
import java.util.UUID

data class DogDto(
    override val id: UUID,
    override val ownerId: Int,
    override val inZone: Boolean,
    override val tracker: TrackerDto,
    override val type: PetType = PetType.DOG
) : PetDto(id, type, ownerId, tracker, inZone)
