package com.rusab.grail.takehometest.fixtures

import com.rusab.grail.takehometest.models.Address
import com.rusab.grail.takehometest.models.Participant
import java.sql.Date
import java.time.LocalDate
import java.time.Month

fun generateParticipantWithValidData(): Participant {
    return Participant(
        name = "John Doe",
        dateOfBirth = LocalDate.of(1991, Month.APRIL, 1),
        address = Address("Baker Street", "99501"),
        phoneNumber = "(123) 123-1234",
        referenceNumber = "KTPL-8692"
    )
}

fun generateParticipantWithInvalidZipCode(): Participant {
    return Participant(
        name = "John Doe",
        dateOfBirth = LocalDate.of(1991, Month.APRIL, 1),
        address = Address("Baker Street", "NotAZipCode"),
        phoneNumber = "(123) 123-1234",
        referenceNumber = "KTPL-8692"
    )
}

fun generateParticipantWithInvalidPhoneNumber() : Participant {
    return Participant(
        name = "John Doe",
        dateOfBirth = LocalDate.of(1991, Month.APRIL, 1),
        address = Address("Baker Street", "99501"),
        phoneNumber = "(123)9761234",
        referenceNumber = "KTPL-8692"
    )
}
