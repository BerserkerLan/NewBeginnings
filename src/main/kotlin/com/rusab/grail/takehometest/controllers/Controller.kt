package com.rusab.grail.takehometest.controllers

import com.rusab.grail.takehometest.exceptions.InvalidUpdateException
import com.rusab.grail.takehometest.models.Participant
import com.rusab.grail.takehometest.models.ParticipantUpdate
import com.rusab.grail.takehometest.services.participantservice.ParticipantService
import com.rusab.grail.takehometest.services.referencenumberservice.ReferenceNumberService
import com.rusab.grail.takehometest.services.validationservice.ValidationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class Controller {

    @Autowired
    private lateinit var participantService: ParticipantService

    @Autowired
    private lateinit var referenceNumberService: ReferenceNumberService

    @Autowired
    private lateinit var validationService: ValidationService

    /**
     * POST endpoint to enroll a new participant
     * The reference Number is generated from the referenceNumberService, but it could be retrieved from an external API as well
     * @return referenceNumber returns the referenceNumber that this participant was registered with
     */
    @PostMapping("/enroll")
    fun enrollParticipant(@RequestBody participant: Participant): String {
        // The assignment said it can be assumed this reference number is provided by a seperate microservice with an API, I have simply just set up a service for it
        participant.referenceNumber = referenceNumberService.generateReferenceNumber()
        validationService.validateParticipant(participant)
        return participantService.enrollParticipant(participant)
    }

    /**
     * PUT endpoint to update an existing participant, will throw a [ParticipantNotFoundException] if not able to find it first
     * @param referenceNumber the number to use to find the participant in the service to update
     * @param participantUpdate an object that contains information that should be updated for the participant
     */
    @PutMapping("update/participant/{referenceNumber}")
    fun updateParticipant(@PathVariable("referenceNumber") referenceNumber: String, @RequestBody participantUpdate: ParticipantUpdate) {
        if (participantUpdate.address == null && participantUpdate.phoneNumber == null) {
            throw InvalidUpdateException(referenceNumber)
        }
        participantService.updateParticipantDetails(participantUpdate, referenceNumber)
    }

    /**
     * DELETE endpoint to delete an existing participant, will throw a [ParticipantNotFoundException] if not able to find it first
     * @param referenceNumber the number to use to find the participant in the service to update
     */
    @DeleteMapping("/remove/{referenceNumber}")
    fun removeParticipant(@PathVariable("referenceNumber") referenceNumber: String) {
        participantService.removeParticipant(referenceNumber)
    }

    /**
     * GET endpoint to retrieve a participant with the reference number
     * @param referenceNumber the number to use to find the participant in the service to update
     */
    @GetMapping("/participant/{referenceNumber}")
    fun getParticipant(@PathVariable("referenceNumber") referenceNumber: String): Participant {
        return participantService.getParticipantByReferenceNumber(referenceNumber)
    }
}