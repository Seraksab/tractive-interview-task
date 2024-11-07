package com.tractive.pettracking.repository

import com.tractive.pettracking.model.Pet
import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetRepository : PagingAndSortingRepository<Pet, UUID>, ListCrudRepository<Pet, UUID>
