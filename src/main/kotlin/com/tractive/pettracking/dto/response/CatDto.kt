package com.tractive.pettracking.dto.response

import com.tractive.pettracking.enum.PetType
import java.util.*

data class CatDto(
    override val id: UUID,
    override val ownerId: Int,
    override val tracker: TrackerDto,
    override val inZone: Boolean,
    val lostTracker: Boolean,
    override val type: PetType = PetType.CAT
) : PetDto(id, type, ownerId, tracker, inZone)