package com.rusab.grail.takehometest.controllers

import com.rusab.grail.takehometest.models.Participant
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

    @PostMapping("/enroll")
    fun enrollParticipant(@RequestBody participant: Participant) {
        // The assignment said it can be assumed this reference number is provided by a seperate microservice with an API, I have simply just set up a service for it
        participant.referenceNumber = referenceNumberService.generateReferenceNumber()
        validationService.validateParticipant(participant)
        participantService.enrollParticipant(participant)
    }

    @PostMapping("update/participant/{referenceNumber}")
    fun updateParticipant(@PathVariable("referenceNumber") referenceNumber: String, @RequestBody participant: Participant) {
        val participant = participantService.getParticipantByReferenceNumber(referenceNumber)
    }

    @GetMapping("/participant/{referenceNumber}")
    fun getParticipant(@PathVariable("referenceNumber") referenceNumber: String): Participant {
        return participantService.getParticipantByReferenceNumber(referenceNumber)
    }
}