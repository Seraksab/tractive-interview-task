package com.tractive.pettracking.service

import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.response.PetDto
import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.enum.TrackerType
import com.tractive.pettracking.extension.toDto
import com.tractive.pettracking.model.Cat
import com.tractive.pettracking.model.Dog
import com.tractive.pettracking.model.Pet
import com.tractive.pettracking.model.Tracker
import com.tractive.pettracking.repository.PetRepository
import com.tractive.pettracking.repository.TrackerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

@ExtendWith(MockitoExtension::class)
class PetServiceTest {

    @Mock
    lateinit var petRepository: PetRepository

    @Mock
    lateinit var trackerRepository: TrackerRepository

    @InjectMocks
    lateinit var petService: PetServiceImpl

    @Test
    fun `getPetsPaged returns all pets in pages`() {
        val pets: List<Pet> = listOf(
            Dog(1, Tracker(TrackerType.DOG_SMALL), false),
            Cat(1, false, Tracker(TrackerType.CAT_LARGE), false)
        )
        val pageable: Pageable = Pageable.unpaged()
        Mockito.`when`(petRepository.findAll(pageable)).thenReturn(PageImpl(pets, pageable, pets.size.toLong()))

        val page: Page<PetDto> = petService.getPetsPaged(pageable)
        assertNotNull(page)
        assertEquals(2, page.totalElements)
        assertEquals(pets[0].toDto(), page.content[0])
    }

    @Test
    fun `getPetById returns pet`() {
        val pet: Pet = Dog(1, Tracker(TrackerType.DOG_SMALL), false)
        Mockito.`when`(petRepository.findById(pet.id)).thenReturn(Optional.of(pet))

        val returnedPet = petService.getPetById(pet.id)
        assertTrue(returnedPet.isPresent)
        assertEquals(pet.id, returnedPet.get().id)
        assertEquals(pet.toDto(), returnedPet.get())
    }

    @Test
    fun `getPetById returns empty result when pet not found`() {
        Mockito.`when`(petRepository.findById(any(UUID::class.java))).thenReturn(Optional.empty())

        val returnedPet = petService.getPetById(UUID.randomUUID())
        assertTrue(returnedPet.isEmpty)
    }

    @Test
    fun `getPetById with unknown id returns empty result`() {
        val id = UUID.randomUUID()
        Mockito.`when`(petRepository.findById(id)).thenReturn(Optional.empty())

        val returnedPet = petService.getPetById(id)
        assertFalse(returnedPet.isPresent)
    }

    @Test
    fun `createPet creates new pet`() {
        val tracker = Tracker(TrackerType.CAT_SMALL)
        Mockito.`when`(trackerRepository.save(Mockito.any(Tracker::class.java))).thenReturn(tracker)

        val cat = Cat(1, false, tracker, false)
        Mockito.`when`(petRepository.save(Mockito.any(Cat::class.java))).thenReturn(cat)

        val createPetDto = CreatePetDto(1, PetType.CAT, tracker.type)
        val createdPet = petService.createPet(createPetDto)
        assertEquals(cat.id, createdPet.id)
        assertEquals(createPetDto.petType, createdPet.type)
    }

    @Test
    fun `deletePetById throws NOT FOUND when pet not exists`() {
        val id = UUID.randomUUID()
        Mockito.`when`(petRepository.existsById(id)).thenReturn(false)

        val exception = assertThrows(ResponseStatusException::class.java) {
            petService.deletePetById(id)
        }
        assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
    }
}
