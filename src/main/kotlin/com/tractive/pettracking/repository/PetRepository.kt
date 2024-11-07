package com.tractive.pettracking.repository

import com.tractive.pettracking.dto.response.OutsideZoneDto
import com.tractive.pettracking.model.Pet
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetRepository : PagingAndSortingRepository<Pet, UUID>, ListCrudRepository<Pet, UUID> {

    fun findPetByTrackerId(trackerId: UUID): Optional<Pet>

    @Query(
        """
        SELECT new com.tractive.pettracking.dto.response.OutsideZoneDto(p.type, p.tracker.type, COUNT(p))
        FROM Pet p
        WHERE NOT p.inZone
        GROUP BY p.type, p.tracker.type
    """
    )
    fun countOutsideZoneByTypeAndTrackerType(): List<OutsideZoneDto>
}
