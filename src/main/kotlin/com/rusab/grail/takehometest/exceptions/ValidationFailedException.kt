package com.rusab.grail.takehometest.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception to throw if a participant fails validation
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ValidationFailedException(private val referenceNumber: String?) : Exception() {

    override val message: String
        get() = "Validation Failed for participant with referenceNumber: $referenceNumber"
}