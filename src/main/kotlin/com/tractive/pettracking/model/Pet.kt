package com.tractive.pettracking.model

import com.tractive.pettracking.enum.PetType
import jakarta.persistence.*
import java.util.UUID

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
abstract class Pet(

    @Column(nullable = false)
    val ownerId: Int,

    @OneToOne(fetch = FetchType.EAGER)
    val tracker: Tracker,

    var inZone: Boolean

    // + additional general pet data like e.g.: name, date of birth, etc.

) {

    @Id
    @GeneratedValue
    val id: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    lateinit var type: PetType
}
