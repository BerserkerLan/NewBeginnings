package com.rusab.grail.takehometest.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.rusab.grail.takehometest.fixtures.generateParticipantWithValidData
import com.rusab.grail.takehometest.models.Address
import com.rusab.grail.takehometest.models.Participant
import com.rusab.grail.takehometest.models.ParticipantUpdate
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.assertj.core.api.Assertions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private  lateinit var  mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @DirtiesContext
    @Test
    fun test_enrollParticipantSucceeds_andAbleToGetDetails() {
        val participant = generateParticipantWithValidData()

        val mvcPostResult = mockMvc.perform(post("/enroll").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(participant)))
            .andExpect(status().isOk).andReturn()
        val referenceNumberSaved = mvcPostResult.response.contentAsString

        val mvcGetResult = mockMvc.perform(get("/participant/$referenceNumberSaved")).andExpect(status().isOk).andReturn()

        val participantSaved = objectMapper.readValue(mvcGetResult.response.contentAsString, Participant::class.java)

        Assertions.assertThat(participant.name).isEqualTo(participantSaved.name)
        Assertions.assertThat(participant.dateOfBirth).isEqualTo(participantSaved.dateOfBirth)
        Assertions.assertThat(participant.phoneNumber).isEqualTo(participantSaved.phoneNumber)
        Assertions.assertThat(participant.address).isEqualTo(participantSaved.address)
    }

    @DirtiesContext
    @Test
    fun test_updateParticipantSucceeds_IfParticipantExists() {
        val participant = generateParticipantWithValidData()

        val mvcPostResult = mockMvc.perform(post("/enroll").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(participant)))
            .andExpect(status().isOk).andReturn()

        val referenceNumberSaved = mvcPostResult.response.contentAsString

        val participantUpdate = ParticipantUpdate("(123) 123-4567", Address("Jane Street", "99502"))

        mockMvc.perform(put("/update/participant/$referenceNumberSaved").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(participantUpdate)))
            .andExpect(status().isOk)

        val mvcGetResult = mockMvc.perform(get("/participant/$referenceNumberSaved")).andExpect(status().isOk).andReturn()
        val participantSaved = objectMapper.readValue(mvcGetResult.response.contentAsString, Participant::class.java)

        Assertions.assertThat(participantSaved.phoneNumber).isEqualTo(participantUpdate.phoneNumber)
        Assertions.assertThat(participantSaved.address).isEqualTo(participantUpdate.address)

    }

    @DirtiesContext
    @Test
    fun test_updateParticipantFails_IfParticipantNotExist() {
        val participant = generateParticipantWithValidData()

        val participantUpdate = ParticipantUpdate("(123) 123-4567", Address("Jane Street", "99502"))

        mockMvc.perform(put("/update/participant/${participant.referenceNumber}").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(participantUpdate)))
            .andExpect(status().isNotFound)

    }

    @DirtiesContext
    @Test
    fun test_removeParticipantSucceeds_IfParticipantExists() {
        val participant = generateParticipantWithValidData()

        val mvcPostResult = mockMvc.perform(post("/enroll").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(participant)))
            .andExpect(status().isOk).andReturn()
        val referenceNumberSaved = mvcPostResult.response.contentAsString

        mockMvc.perform(delete("/remove/$referenceNumberSaved")).andExpect(status().isOk)
    }

    @DirtiesContext
    @Test
    fun test_removeParticipantFails_IfParticipantDoesNotExist() {
        val participant = generateParticipantWithValidData()

        mockMvc.perform(delete("/remove/${participant.referenceNumber}")).andExpect(status().isNotFound)
    }

}