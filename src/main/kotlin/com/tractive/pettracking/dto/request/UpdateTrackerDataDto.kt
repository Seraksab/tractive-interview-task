package com.tractive.pettracking.dto.request

data class UpdateTrackerDataDto(
    val inZone: Boolean,
    val lostTracker: Boolean?
)
