package com.tractive.pettracking.service

import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.dto.response.OutsideZoneDto
import com.tractive.pettracking.dto.response.PetDto
import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.extension.toDto
import com.tractive.pettracking.model.Cat
import com.tractive.pettracking.model.Dog
import com.tractive.pettracking.model.Pet
import com.tractive.pettracking.model.Tracker
import com.tractive.pettracking.repository.PetRepository
import com.tractive.pettracking.repository.TrackerRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class PetServiceImpl(
    private val petRepository: PetRepository,
    private val trackerRepository: TrackerRepository
) : PetService {

    private val logger = LoggerFactory.getLogger(PetServiceImpl::class.java)

    @Transactional(readOnly = true)
    override fun getPetsPaged(pageable: Pageable): Page<PetDto> {
        return petRepository.findAll(pageable).map { it.toDto() }
    }

    @Transactional(readOnly = true)
    override fun getPetById(id: UUID): Optional<PetDto> {
        return petRepository.findById(id).map { it.toDto() }
    }

    @Transactional
    override fun createPet(dto: CreatePetDto): PetDto {
        // create a new tracker first
        val tracker = trackerRepository.save(Tracker(dto.trackerType))
        // create new pet
        val pet = petRepository.save(createPetEntity(dto, tracker))
        logger.info("New Pet created: {}", pet.id)
        return pet.toDto()
    }

    @Transactional
    override fun deletePetById(id: UUID) {
        if (!petRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        petRepository.deleteById(id)
        logger.info("Pet deleted: {}", id)
    }

    @Transactional
    override fun updateTrackerData(trackerId: UUID, dto: UpdateTrackerDataDto) {
        val pet = petRepository.findPetByTrackerId(trackerId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Tracker not found") }

        pet.inZone = dto.inZone
        if (pet is Cat && dto.lostTracker != null) {
            pet.lostTracker = dto.lostTracker
        }

        petRepository.save(pet)
    }

    override fun countPetsOutsideZone(): List<OutsideZoneDto> {
        return petRepository.countOutsideZoneByTypeAndTrackerType()
    }

    private fun createPetEntity(dto: CreatePetDto, tracker: Tracker): Pet = when (dto.petType) {
        PetType.DOG -> Dog(
            ownerId = dto.ownerId,
            inZone = false,
            tracker = tracker
        )

        PetType.CAT -> Cat(
            ownerId = dto.ownerId,
            inZone = false,
            tracker = tracker,
            lostTracker = false
        )

        else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported pet type")
    }

}