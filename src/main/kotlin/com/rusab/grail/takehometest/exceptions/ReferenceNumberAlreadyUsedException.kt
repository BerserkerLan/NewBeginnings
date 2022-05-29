package com.rusab.grail.takehometest.exceptions

/**
 * Exception thrown when a referenceNumber has already been used in the system
 */
class ReferenceNumberAlreadyUsedException(private val referenceNumber: String?) : Exception() {
    override val message: String
        get() = "This reference Number is already in use, referenceNumber: $referenceNumber"
}