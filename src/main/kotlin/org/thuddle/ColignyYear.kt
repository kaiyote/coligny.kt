package org.thuddle

import kotlin.math.abs

class ColignyYear(val year: Int, val isMetonic: Boolean = false) {
    companion object {
        private val metonicMap: Map<Int, Int> = mapOf(
            Pair(-18, 3), Pair(-17, 4), Pair(-16, 5),
            Pair(-15, 1), Pair(-14, 2), Pair(-13, 3), Pair(-12, 4), Pair(-11, 5),
            Pair(-10, 1), Pair(-9, 2), Pair(-8, 3), Pair(-7, 4), Pair(-6, 5),
            Pair(-5, 1), Pair(-4, 2), Pair(-3, 3), Pair(-2, 4), Pair(-1, 5),
            Pair(0, 2), Pair(1, 3), Pair(2, 4), Pair(3, 5),
            Pair(4, 1), Pair(5, 2), Pair(6, 3), Pair(7, 4), Pair(8, 5),
            Pair(9, 1), Pair(10, 2), Pair(11, 3), Pair(12, 4), Pair(13, 5),
            Pair(14, 1), Pair(15, 2), Pair(16, 3), Pair(17, 4), Pair(18, 5)
        )

        private val saturnMap: Map<Int, Int> = mapOf(
            Pair(-4, 2), Pair(-3, 3), Pair(-2, 4), Pair(-1, 5),
            Pair(0, 1), Pair(1, 2), Pair(2, 3), Pair(3, 4), Pair(4, 5)
        )
    }

    val months: ArrayList<ColignyMonth> = arrayListOf(
        ColignyMonth("Samonios", 30),
        ColignyMonth("Dumanios", 29),
        ColignyMonth("Riuros", 30),
        ColignyMonth("Anagantios", 29),
        ColignyMonth("Ogronios", 30),
        ColignyMonth("Cutios", 30),
        ColignyMonth("Giamonios", 29),
        ColignyMonth("Simiuisonna", 30),
        ColignyMonth("Elembi", 29),
        ColignyMonth("Aedrinni", 30),
        ColignyMonth("Cantlos", 29)
    )

    val ident: Int = if (isMetonic) metonicMap.getValue((year - 4999) % 19) else saturnMap.getValue((year - 4998) % 5)
    val yearDays: Int

    init {
        when (ident) {
            1 -> {
                months.add(0, ColignyMonth("Quimonios", 29))
                months.add(8, ColignyMonth("Equos", 30))
            }
            3 -> {
                months.add(8, ColignyMonth("Equos", 29))
                if (isMetonic || (year - 4998) % 30 == 27)
                    months.add(6, ColignyMonth("Rantaranos", 30))
            }
            5 -> months.add(8, ColignyMonth("Equos", 30))
            else -> months.add(8, ColignyMonth("Equos", 29))
        }

        if (isMetonic) {
            val absYear = abs(year - 4999)
            if (absYear >= 60.97 && absYear % 60.97 <= 5 && ident == 5) months[8].days = 29
            if (absYear > 6568.62 && absYear % 6568.62 <= 5 && ident == 3) months.removeAt(6)
        } else {
            val absYear = abs(year - 4998)
            if (absYear >= 197.97 && absYear % 197.97 <= 5 && ident == 5) months[8].days = 29
            if (absYear >= 635.04 && absYear % 635.04 <= 30 && absYear % 30 == 27) months.add(6, ColignyMonth("Rantaranos", 30))
        }

        months.forEachIndexed { index, colignyMonth ->  colignyMonth.index = index }

        yearDays = months.sumBy { it.days }
    }
}