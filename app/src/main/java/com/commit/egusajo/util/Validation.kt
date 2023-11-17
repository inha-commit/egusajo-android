package com.commit.egusajo.util

object Validation {
    private val linkRegex = Regex("^https://.*$")
    private val birthRegex = Regex("""^\d{4}/\d{2}/\d{2}${'$'}""")

    fun validateLink(link: String): Boolean {
        return link.matches(linkRegex)
    }

    fun validateDate(date: String): Boolean {
        return date.matches(birthRegex)
    }

    fun validateMoney(money: String): Boolean {
        return money.all { it.isDigit() }
    }
}