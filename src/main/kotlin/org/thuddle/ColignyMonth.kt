package org.thuddle

data class ColignyMonth(val name: String, var days: Int) {
    var index: Int = 0
    val omen: String = if (days == 30) "Mat" else "Anmat"
}