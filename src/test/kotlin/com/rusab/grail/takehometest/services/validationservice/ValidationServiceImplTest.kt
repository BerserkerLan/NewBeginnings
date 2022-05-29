package com.rusab.grail.takehometest.services.validationservice

import com.rusab.grail.takehometest.exceptions.ParticipantNotFoundException
import com.rusab.grail.takehometest.exceptions.ReferenceNumberAlreadyUsedException
import com.rusab.grail.takehometest.exceptions.ValidationFailedException
import com.rusab.grail.takehometest.fixtures.generateParticipantWithInvalidPhoneNumber
import com.rusab.grail.takehometest.fixtures.generateParticipantWithInvalidZipCode
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
class ValidationServiceImplTest {

    @Autowired
    private lateinit var validationService: ValidationService

    @Test
    fun validationSucceeds_ifValidParticipant() {
        val participant = generateParticipantWithValidData()
        Assertions.assertThatNoException().isThrownBy { validationService.validateParticipant(participant) }
    }

    @Test
    fun validationFails_ifInvalidPhoneNumberFormatForParticipant() {
        val participant = generateParticipantWithInvalidPhoneNumber()
        assertThrows<ValidationFailedException> { validationService.validateParticipant(participant) }
    }

    @Test
    fun validationFails_ifInvalidZipCodeForParticipant() {
        val participant = generateParticipantWithInvalidZipCode()
        assertThrows<ValidationFailedException> { validationService.validateParticipant(participant) }
    }
}