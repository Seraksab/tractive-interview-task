package com.tractive.pettracking.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.response.DogDto
import com.tractive.pettracking.dto.response.OutsideZoneDto
import com.tractive.pettracking.dto.response.PetDto
import com.tractive.pettracking.dto.response.TrackerDto
import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.enum.TrackerType
import com.tractive.pettracking.service.PetService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(PetController::class)
class PetControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var petService: PetService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val petDto = DogDto(
        UUID.randomUUID(),
        ownerId = 1,
        tracker = TrackerDto(UUID.randomUUID(), TrackerType.DOG_SMALL),
        inZone = false
    )
    private val createPetDto = CreatePetDto(ownerId = 1, petType = PetType.DOG, trackerType = TrackerType.DOG_SMALL)
    private val outsideZoneDtos = listOf(OutsideZoneDto(PetType.DOG, TrackerType.DOG_SMALL, 1))

    @Test
    fun `when get all pets paged then return success`() {
        val pageRequest = PageRequest.of(0, 20)
        val page: Page<PetDto> = PageImpl(listOf(petDto), pageRequest, 1)

        `when`(petService.getPetsPaged(pageRequest)).thenReturn(page)

        mockMvc.perform(get("/pets"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(page)))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.totalElements").value(1))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.content.length()").value(1))
            .andExpect(jsonPath("$.numberOfElements").value(1))
    }

    @Test
    fun `when create a pet then return created`() {
        `when`(petService.createPet(createPetDto)).thenReturn(petDto)

        mockMvc.perform(
            post("/pets")
                .content(objectMapper.writeValueAsString(createPetDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(petDto)))
            .andExpect(jsonPath("$.id").isNotEmpty)
            .andExpect(jsonPath("$.ownerId").value(createPetDto.ownerId))
            .andExpect(jsonPath("$.type").value(createPetDto.petType.name))
    }

    @Test
    fun `when create invalid pet then return bad request`() {
        val invalidDto = CreatePetDto(-1, PetType.DOG, TrackerType.DOG_SMALL)
        mockMvc.perform(
            post("/pets")
                .content(objectMapper.writeValueAsString(invalidDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `when delete a pet then return no content`() {
        val petId = petDto.id

        doNothing().`when`(petService).deletePetById(petId)

        mockMvc.perform(delete("/pets/$petId"))
            .andExpect(status().isNoContent)
    }

    @Test
    fun `when get pets outside zone then return success`() {
        `when`(petService.countPetsOutsideZone()).thenReturn(outsideZoneDtos)

        mockMvc.perform(get("/pets/outsideZone"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(outsideZoneDtos)))
            .andExpect(jsonPath("$.length()").value(1))
    }
}