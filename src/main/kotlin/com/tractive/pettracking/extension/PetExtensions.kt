package com.tractive.pettracking.extension

import com.tractive.pettracking.dto.response.CatDto
import com.tractive.pettracking.dto.response.DogDto
import com.tractive.pettracking.dto.response.PetDto
import com.tractive.pettracking.dto.response.TrackerDto
import com.tractive.pettracking.model.Cat
import com.tractive.pettracking.model.Dog
import com.tractive.pettracking.model.Pet

fun Pet.toDto(): PetDto = when (this) {
    is Dog -> DogDto(
        id = this.id,
        ownerId = this.ownerId,
        inZone = this.inZone,
        tracker = TrackerDto(
            this.tracker.id,
            this.tracker.type
        )
    )

    is Cat -> CatDto(
        id = this.id,
        ownerId = this.ownerId,
        inZone = this.inZone,
        tracker = TrackerDto(
            this.tracker.id,
            this.tracker.type
        ),
        lostTracker = this.lostTracker
    )

    else -> throw IllegalArgumentException("Unknown pet type")
}