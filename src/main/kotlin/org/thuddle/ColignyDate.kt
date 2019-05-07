package org.thuddle

import java.lang.Math.abs
import java.time.LocalDate

class ColignyDate(var year: Int, month: Int, var day: Int, val isMetonic: Boolean = false) {
    val fullYear: ColignyYear = ColignyYear(year, isMetonic)
    val month: ColignyMonth = fullYear.months[month]
    var yearToBegin: Int = 0
    val yearToEnd: Int

    init {
        for ((index, value) in fullYear.months.withIndex()) {
            if (index == this.month.index) {
                yearToBegin += day
                break
            } else {
                yearToBegin += value.days
            }
        }

        yearToEnd = fullYear.yearDays - yearToBegin
    }

    fun string(cycle: Boolean = false): String = "${month.name} $day ${if (cycle) fullYear.ident else year}"

    fun calcDays(add: Int): ColignyDate {
        var output = ColignyDate(year, this.month.index, day, isMetonic)
        output.day += add

        while (output.day > output.month.days) {
            if (output.month.index == output.fullYear.months.size - 1) output = ColignyDate(output.year + 1, 0, output.day - output.month.days, isMetonic)
            else output = ColignyDate(output.year, output.month.index + 1, output.day - output.month.days, isMetonic)
        }

        while (output.day < 1) {
            if (output.month.index == 0) {
                val newLength = ColignyYear(output.year - 1, isMetonic).months.size
                output = ColignyDate(output.year - 1, newLength - 1, output.day + output.month.days, isMetonic)
            } else {
                output = ColignyDate(output.year, output.month.index - 1, output.day + output.month.days, isMetonic)
            }
        }

        return output
    }

    override fun equals(other: Any?): Boolean {
        if (other !is ColignyDate) return false

        return year == other.year && this.month.index == other.month.index && day == other.day && isMetonic == other.isMetonic
    }

    fun before(other: ColignyDate): Boolean {
        if (isMetonic != other.isMetonic) throw IllegalArgumentException("Cannot compare dates of different cycle types.")
        if (equals(other)) return false
        if (year > other.year) return false
        if (year < other.year) return true
        if (this.month.index > other.month.index) return false
        if (this.month.index < other.month.index) return true
        if (day > other.day) return false
        return true
    }

    fun difference(other: ColignyDate): Int {
        if (isMetonic != other.isMetonic) throw IllegalArgumentException("Cannot compare dates of different cycle types.")
        if (equals(other)) return 0
        if (year == other.year && this.month.index == other.month.index) return abs(day - other.day)
        if (other.before(this)) return other.difference(this)

        var count = 0

        if (year == other.year) {
            count += this.month.days - day + other.day

            for ((index, value) in fullYear.months.withIndex()) {
                if (index <= this.month.index) continue
                if (index >= other.month.index) break
                count += value.days
            }
        } else {
            count += yearToEnd + other.yearToBegin
            for (i in year+1..other.year) {
                val current = ColignyYear(i, isMetonic)
                count += current.yearDays
            }
        }

        return count
    }

    fun toGregorianDate(): LocalDate {
        val start = ColignyDate(if (isMetonic) 4999 else 4998, 0, 1, isMetonic)
        val gregDate = if (isMetonic) LocalDate.of(1999, 4, 22) else LocalDate.of(1998, 4, 3)

        val change = difference(start)
        return if (before(start)) gregDate.minusDays(change.toLong()) else gregDate.plusDays(change.toLong())
    }
}