package com.tractive.pettracking.model

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("CAT")
class Cat(
    ownerId: Int,
    inZone: Boolean,
    tracker: Tracker,

    var lostTracker: Boolean

    // + additional cat specific data like e.g.: breed, etc.

) : Pet(ownerId, tracker, inZone)
