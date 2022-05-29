package com.rusab.grail.takehometest.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception thrown if a participant is not found in the system when we try to access them
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ParticipantNotFoundException(private val referenceNumber: String?) : Exception() {

    override val message: String
        get() = "Cannot find participant with referenceNumber: $referenceNumber"
}