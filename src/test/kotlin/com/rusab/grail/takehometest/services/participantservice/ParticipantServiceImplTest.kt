package com.rusab.grail.takehometest.services.participantservice

import com.rusab.grail.takehometest.exceptions.ParticipantNotFoundException
import com.rusab.grail.takehometest.exceptions.ReferenceNumberAlreadyUsedException
import com.rusab.grail.takehometest.fixtures.generateParticipantWithValidData
import com.rusab.grail.takehometest.models.Address
import com.rusab.grail.takehometest.models.ParticipantUpdate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
class ParticipantServiceImplTest {

    @Autowired
    private lateinit var participantService: ParticipantService

    @DirtiesContext
    @Test
    fun test_EnrollParticipantSucceeds() {
        val participant = generateParticipantWithValidData()
        val referenceNumber = participantService.enrollParticipant(participant)

        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).name).isEqualTo(participant.name)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).dateOfBirth).isEqualTo(participant.dateOfBirth)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).phoneNumber).isEqualTo(participant.phoneNumber)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).address).isEqualTo(participant.address)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).referenceNumber).isEqualTo(participant.referenceNumber)
    }

    @DirtiesContext
    @Test
    fun test_EnrollParticipantThrowsException_ifAlreadyRegisteredWithReferenceNumber() {
        val participant = generateParticipantWithValidData()
        participantService.enrollParticipant(participant)

        assertThrows<ReferenceNumberAlreadyUsedException> { participantService.enrollParticipant(participant) }
    }

    @DirtiesContext
    @Test
    fun test_UpdateParticipantSucceeds_forPhoneNumber_ifParticipantExists() {
        val participant = generateParticipantWithValidData()
        val referenceNumber = participantService.enrollParticipant(participant)
        val newPhoneNumber = "(123) 123-4567"
        participantService.updateParticipantDetails(ParticipantUpdate(newPhoneNumber), referenceNumber)

        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).phoneNumber).isEqualTo(newPhoneNumber)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).address).isEqualTo(participant.address)
    }

    @DirtiesContext
    @Test
    fun test_UpdateParticipantSucceeds_forAddress_ifParticipantExists() {
        val participant = generateParticipantWithValidData()
        val referenceNumber = participantService.enrollParticipant(participant)
        val newAddress = Address("Butcher Street", "99504")
        participantService.updateParticipantDetails(ParticipantUpdate(null, newAddress), referenceNumber)

        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).address).isEqualTo(newAddress)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).phoneNumber).isEqualTo(participant.phoneNumber)
    }

    @DirtiesContext
    @Test
    fun test_UpdateParticipantSucceeds_forAddressAndPhoneNumber_ifParticipantExists() {
        val participant = generateParticipantWithValidData()
        val referenceNumber = participantService.enrollParticipant(participant)
        val newAddress = Address("Butcher Street", "99504")
        val newPhoneNumber = "(123) 123-4567"
        participantService.updateParticipantDetails(ParticipantUpdate(newPhoneNumber, newAddress), referenceNumber)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).address).isEqualTo(newAddress)
        Assertions.assertThat(participantService.getParticipantByReferenceNumber(referenceNumber).phoneNumber).isEqualTo(newPhoneNumber)
    }

    @DirtiesContext
    @Test
    fun test_UpdateParticipantFails_forAddressAndPhoneNumber_ifParticipantDoesNotExist() {
        val newAddress = Address("Butcher Street", "99504")
        val newPhoneNumber = "(123) 123-4567"
        val someReferenceNumber = "GHLK-1927"

        assertThrows<ParticipantNotFoundException> {  participantService.updateParticipantDetails(ParticipantUpdate(newPhoneNumber, newAddress), someReferenceNumber) }
    }

    @DirtiesContext
    @Test
    fun test_RemoveParticipantSucceeds_ifParticipantExists() {
        val participant = generateParticipantWithValidData()
        val referenceNumber = participantService.enrollParticipant(participant)

        participantService.removeParticipant(referenceNumber)

        assertThrows<ParticipantNotFoundException> { participantService.getParticipantByReferenceNumber(referenceNumber) }
    }

    @DirtiesContext
    @Test
    fun test_RemoveParticipantFails_ifParticipantDoesNotExist() {
        val participant = generateParticipantWithValidData()
        assertThrows<ParticipantNotFoundException> { participantService.removeParticipant(participant.referenceNumber!!) }

    }

}