package com.tractive.pettracking.controller

import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.response.OutsideZoneDto
import com.tractive.pettracking.dto.response.PetDto
import com.tractive.pettracking.service.PetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Tag(name = "Pets")
@RestController
@RequestMapping("/pets")
class PetController(
    private val petService: PetService
) {

    @Operation(summary = "Get all pets [paged]")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllPetsPaged(pageable: Pageable): Page<PetDto> {
        return petService.getPetsPaged(pageable)
    }

    @Operation(summary = "Create a new pet")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPet(@Valid @RequestBody dto: CreatePetDto): PetDto {
        return petService.createPet(dto)
    }

    @Operation(summary = "Delete a pet")
    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePet(@PathVariable petId: UUID) {
        petService.deletePetById(petId)
    }

    @Operation(summary = "Get all pets outside their zone grouped by pet type and tracker type")
    @GetMapping("/outsideZone", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun countOutsideZone(): List<OutsideZoneDto> {
        return petService.countPetsOutsideZone()
    }
}