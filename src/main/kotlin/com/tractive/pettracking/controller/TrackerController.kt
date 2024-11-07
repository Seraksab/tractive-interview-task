package com.tractive.pettracking.controller

import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.service.PetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@Tag(name = "Trackers")
@RestController
@RequestMapping("/trackers")
class TrackerController(
    private val petService: PetService
) {

    @Operation(summary = "Update data of a tracker")
    @PutMapping("/{trackerId}/data", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateTrackerData(
        @PathVariable trackerId: UUID,
        @Valid @RequestBody dto: UpdateTrackerDataDto
    ) {
        petService.updateTrackerData(trackerId, dto)
    }
}