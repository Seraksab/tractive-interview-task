package com.tractive.pettracking.model

import com.tractive.pettracking.enum.PetType
import jakarta.persistence.Entity

@Entity
class Cat(
    ownerId: Int,
    inZone: Boolean,
    tracker: Tracker,

    var lostTracker: Boolean

    // + additional cat specific data like e.g.: breed, etc.

) : Pet(PetType.CAT, ownerId, tracker, inZone)
