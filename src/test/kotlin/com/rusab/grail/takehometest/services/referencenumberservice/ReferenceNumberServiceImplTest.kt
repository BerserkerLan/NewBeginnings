package com.rusab.grail.takehometest.services.referencenumberservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions

@SpringBootTest
class ReferenceNumberServiceImplTest {

    @Autowired
    private lateinit var referenceNumberService: ReferenceNumberService
    @Test
    fun generateReferenceNumber_generatesUniqueReferenceNumber() {
        val referenceNumber1 = referenceNumberService.generateReferenceNumber()
        val referenceNumber2 = referenceNumberService.generateReferenceNumber()

        Assertions.assertThat(referenceNumber1).isNotEqualTo(referenceNumber2)

    }

}