package com.rusab.grail.takehometest.services.participantservice

import com.rusab.grail.takehometest.models.Participant

interface ParticipantService {

    fun enrollParticipant(participant: Participant)

    fun getParticipantByReferenceNumber(referenceNumber: String): Participant
}