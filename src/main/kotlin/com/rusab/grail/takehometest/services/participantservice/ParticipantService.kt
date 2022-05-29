package com.rusab.grail.takehometest.services.participantservice

import com.rusab.grail.takehometest.models.Participant
import com.rusab.grail.takehometest.models.ParticipantUpdate

interface ParticipantService {

    /**
     * Enroll the participant into the system
     */
    fun enrollParticipant(participant: Participant): String

    /**
     * Attempt to find the participant using its reference number
     */
    fun getParticipantByReferenceNumber(referenceNumber: String): Participant

    /**
     * Update any details for the participant by discovering it with it's referenceNumber and then updating whatever is specificied in the participantUpdate object
     */
    fun updateParticipantDetails(participantUpdate: ParticipantUpdate, referenceNumber: String)

    /**
     * Attempt to remove the participant using its reference number
     */
    fun removeParticipant(referenceNumber: String)
}