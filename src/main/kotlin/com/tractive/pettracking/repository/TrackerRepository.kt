package com.tractive.pettracking.repository

import com.tractive.pettracking.model.Tracker
import org.springframework.data.repository.ListCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TrackerRepository : ListCrudRepository<Tracker, UUID>
