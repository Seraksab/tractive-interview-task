package com.tractive.pettracking.model

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("DOG")
class Dog(
    ownerId: Int,
    tracker: Tracker,
    inZone: Boolean,

    // + additional dog specific data like e.g.: breed, etc.

) : Pet(ownerId, tracker, inZone)
