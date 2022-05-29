package com.rusab.grail.takehometest.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Thrown if the update request does not have any parameters to actually update, if they are all null
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidUpdateException(private val referenceNumber: String?) : Exception() {
    override val message: String
        get() = "Cannot update participant as it is lacking update parameters, referenceNumber: $referenceNumber"
}