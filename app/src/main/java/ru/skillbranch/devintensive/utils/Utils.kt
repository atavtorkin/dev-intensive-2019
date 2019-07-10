package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? = fullName?.takeIf { it.isNotBlank() }?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String? {
        return String()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstLetter = firstName?.takeIf { it.isNotBlank() }?.substring(0, 1)?.toUpperCase() ?: ""
        val lastLetter = lastName?.takeIf { it.isNotBlank() }?.substring(0, 1)?.toUpperCase() ?: ""

        return "$firstLetter$lastLetter".takeIf { it.isNotBlank() }
    }

}