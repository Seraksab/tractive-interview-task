package com.tractive.pettracking.model

import com.tractive.pettracking.enum.PetType
import jakarta.persistence.Entity

@Entity
class Dog(
    ownerId: Int,
    tracker: Tracker,
    inZone: Boolean,

    // + additional dog specific data like e.g.: breed, etc.

) : Pet(PetType.DOG, ownerId, tracker, inZone)
