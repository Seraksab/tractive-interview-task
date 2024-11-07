package com.tractive.pettracking.model

import com.tractive.pettracking.enum.PetType
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Pet(

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected open val type: PetType,

    @Column(nullable = false)
    val ownerId: Int,

    @OneToOne(fetch = FetchType.EAGER)
    val tracker: Tracker,

    val inZone: Boolean

    // + additional general pet data like e.g.: name, date of birth, etc.

) {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    val id: Int = 0
}