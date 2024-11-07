package com.tractive.pettracking.controller

import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.response.OutsideZoneDto
import com.tractive.pettracking.dto.response.PetDto
import com.tractive.pettracking.service.PetService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/pets")
class PetController(
    private val petService: PetService
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllPetsPaged(pageable: Pageable): Page<PetDto> {
        return petService.getPetsPaged(pageable)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPet(@RequestBody dto: CreatePetDto): PetDto {
        return petService.createPet(dto)
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePet(@PathVariable petId: UUID) {
        petService.deletePetById(petId)
    }

    @GetMapping("/outsideZone", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun countOutsideZone(): List<OutsideZoneDto> {
        return petService.countPetsOutsideZone()
    }
}