package com.tractive.pettracking.service

import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.dto.response.PetDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface PetService {

    fun getPetsPaged(pageable: Pageable): Page<PetDto>

    fun getPetById(id: UUID): Optional<PetDto>

    fun createPet(dto: CreatePetDto): PetDto

    fun deletePetById(id: UUID)

    fun updateTrackerData(trackerId: UUID, dto: UpdateTrackerDataDto)
}