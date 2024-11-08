package com.tractive.pettracking.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tractive.pettracking.dto.request.UpdateTrackerDataDto
import com.tractive.pettracking.service.PetService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(TrackerController::class)
class TrackerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var petService: PetService

    @Test
    fun `when update tracker data then return no content`() {
        val trackerId = UUID.randomUUID()
        val dataDto = UpdateTrackerDataDto(inZone = true, lostTracker = true)

        mockMvc.perform(
            put("/trackers/$trackerId/data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dataDto))
        )
            .andExpect(status().isNoContent)
    }

    @Test
    fun `when missing tracker data then return bad request`() {
        val trackerId = UUID.randomUUID()
        mockMvc.perform(
            put("/trackers/$trackerId/data")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `when not json then return unsupported media type`() {
        val trackerId = UUID.randomUUID()
        mockMvc.perform(put("/trackers/$trackerId/data"))
            .andExpect(status().isUnsupportedMediaType)
    }
}
