package org.thuddle

import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

fun LocalDate.toColignyDate(isMetonic: Boolean = false): ColignyDate {
    val gregStart = if (isMetonic) LocalDate.of(1999, 4, 22) else LocalDate.of(1998, 4, 3)
    val flip = this < gregStart

    val output = ColignyDate(if (isMetonic) 4999 else 4998, 0, 1, isMetonic)
    val diff = if (flip) DAYS.between(this, gregStart) else DAYS.between(gregStart, this)

    return output.calcDays(diff.toInt())
}