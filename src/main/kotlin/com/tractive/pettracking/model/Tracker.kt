package com.tractive.pettracking.model

import com.tractive.pettracking.enum.TrackerType
import jakarta.persistence.*

@Entity
class Tracker(

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: TrackerType,

    // + additional tracker-specific data like: hardware id, name, inventory number, purchase number etc.
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0
}