package com.tractive.pettracking

import com.fasterxml.jackson.databind.ObjectMapper
import com.tractive.pettracking.dto.request.CreatePetDto
import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.enum.PetType
import com.tractive.pettracking.enum.TrackerType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
// this is just used as a simple way of resetting the DB after each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PetControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val createPetDto = CreatePetDto(ownerId = 1, petType = PetType.DOG, trackerType = TrackerType.DOG_MEDIUM)

    @Test
    fun contextLoads() {
    }

    @Test
    fun `when get pets paged then return correct page`() {
        mockMvc.perform(
            get("/pets")
                .param("page", "1")
                .param("size", "3")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.first").value(false))
            .andExpect(jsonPath("$.last").value(true))
            .andExpect(jsonPath("$.totalElements").value(5))
            .andExpect(jsonPath("$.totalPages").value(2))
            .andExpect(jsonPath("$.size").value(3))
            .andExpect(jsonPath("$.content.length()").value(2))
            .andExpect(jsonPath("$.numberOfElements").value(2))
    }

    @Test
    fun `when create a pet then return created`() {
        mockMvc.perform(
            post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPetDto))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNotEmpty)
            .andExpect(jsonPath("$.ownerId").value(createPetDto.ownerId))
            .andExpect(jsonPath("$.type").value(createPetDto.petType.name))

        mockMvc.perform(get("/pets").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()").value(6))
            .andExpect(jsonPath("$.numberOfElements").value(6))
    }

    @Test
    fun `when create invalid pet then return bad request`() {
        val invalidCreatePetDto = createPetDto.copy(ownerId = -1)
        mockMvc.perform(
            post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCreatePetDto))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `when delete not existing pet then return not found`() {
        mockMvc.perform(delete("/pets/{petId}", UUID.randomUUID()))
            .andExpect(status().isNotFound)
    }


    @Test
    fun `when delete existing pet then pet gets deleted`() {
        mockMvc.perform(get("/pets").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()").value(5))
            .andExpect(jsonPath("$.numberOfElements").value(5))

        mockMvc.perform(delete("/pets/{petId}", "d4d99907-c13e-41d3-be31-40b8742265ed"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/pets").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()").value(4))
            .andExpect(jsonPath("$.numberOfElements").value(4))
    }

    @Test
    fun `when update data of not existing tracker then return not found`() {
        mockMvc.perform(
            put("/trackers/${UUID.randomUUID()}/data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdateTrackerDataDto(inZone = false, lostTracker = false)))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `when update invalid data of tracker then return bad request`() {
        mockMvc.perform(
            put("/trackers/${UUID.randomUUID()}/data")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `when update data of tracker then pet is no longer outside zone`() {

        mockMvc.perform(get("/pets/outsideZone").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(5))

        mockMvc.perform(
            put("/trackers/c8beb646-8f39-462b-841e-1993cdc1a957/data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdateTrackerDataDto(inZone = true, lostTracker = true)))
        )
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/pets/outsideZone").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(4))
    }
}
