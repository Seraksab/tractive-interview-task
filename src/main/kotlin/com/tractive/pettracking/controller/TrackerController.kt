package com.tractive.pettracking.controller

import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.service.PetService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/trackers")
class TrackerController(
    private val petService: PetService
) {

    @PutMapping("/{trackerId}/data", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateTrackerData(
        @PathVariable trackerId: UUID,
        @RequestBody dto: UpdateTrackerDataDto
    ) {
        petService.updateTrackerData(trackerId, dto)
    }
}