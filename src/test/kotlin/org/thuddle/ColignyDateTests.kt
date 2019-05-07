package org.thuddle

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ColignyDateTests {

    @Test
    fun `can get start date`() {
        var date = LocalDate.of(1998, 4, 3).toColignyDate()
        assertEquals(4998, date.year)
        assertEquals(1, date.day)
        assertEquals("Quimonios", date.month.name)

        date = LocalDate.of(1999, 4, 22).toColignyDate(true)
        assertEquals(4999, date.year)
        assertEquals(1, date.day)
        assertEquals("Samonios", date.month.name)
    }

    @Test
    fun `can figure out a date conversion for now-ish`() {
        var date = LocalDate.of(2019, 5, 6).toColignyDate(true)
        assertEquals(5018, date.year)
        assertEquals(25, date.day)
        assertEquals("Cantlos", date.month.name)

        date = LocalDate.of(2019, 5, 6).toColignyDate()
        assertEquals(5018, date.year)
        assertEquals(25, date.day)
        assertEquals("Aedrinni", date.month.name)
    }
}