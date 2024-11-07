package com.tractive.pettracking.service

import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.dto.response.OutsideZoneDto
import com.tractive.pettracking.dto.response.PetDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface PetService {

    /**
     * Retrieves a paginated list of pets in the system.
     *
     * @param pageable the pagination information
     * @return a page of PetDto containing the pet data
     */
    fun getPetsPaged(pageable: Pageable): Page<PetDto>

    /**
     * Retrieves a pet from the system by its ID.
     *
     * @param id the ID of the pet
     * @return an Optional containing the PetDto if a pet with the specified id exists, otherwise an empty Optional
     */
    fun getPetById(id: UUID): Optional<PetDto>

    /**
     * Creates a new pet in the system.
     *
     * @param dto the DTO containing the information required to create the pet
     * @return the created PetDto containing the pet data
     */
    fun createPet(dto: CreatePetDto): PetDto

    /**
     * Deletes a pet from the system by its ID.
     *
     * @param id the ID of the pet to be deleted
     */
    fun deletePetById(id: UUID)

    /**
     * Updates the tracker data for a given tracker.
     *
     * @param trackerId the ID of the tracker to be updated
     * @param dto the DTO containing the updated tracker information
     */
    fun updateTrackerData(trackerId: UUID, dto: UpdateTrackerDataDto)

    /**
     * Counts the pets that are outside their zones grouped by pet type and tracker type.
     *
     * @return the number of pets outside their zone grouped by pet type and tracker type.
     */
    fun countPetsOutsideZone(): List<OutsideZoneDto>
}