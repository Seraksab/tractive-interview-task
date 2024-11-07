package com.tractive.pettracking.dto.response

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.tractive.pettracking.enum.PetType
import java.util.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = CatDto::class, name = "CAT"),
    JsonSubTypes.Type(value = DogDto::class, name = "DOG")
)
sealed class PetDto(
    open val id: UUID,
    open val type: PetType,
    open val ownerId: Int,
    open val tracker: TrackerDto,
    open val inZone: Boolean,
)