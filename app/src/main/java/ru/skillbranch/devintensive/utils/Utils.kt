package ru.skillbranch.devintensive.utils

object Utils {

    private val dictionary = mapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh'",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )

    fun parseFullName(fullName: String?): Pair<String?, String?> {

        val parts: List<String>? = fullName?.takeIf { it.isNotBlank() }?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String? {
        val stringBuilder = StringBuilder()
        payload.forEach { c ->
            when {
                c.isWhitespace() -> stringBuilder.append(divider)
                c.isUpperCase() -> stringBuilder.append((dictionary[c.toLowerCase()]?.toUpperCase() ?: c))
                else -> stringBuilder.append(dictionary[c] ?: c)
            }
        }
        return stringBuilder.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstLetter = firstName?.takeIf { it.isNotBlank() }?.substring(0, 1)?.toUpperCase() ?: ""
        val lastLetter = lastName?.takeIf { it.isNotBlank() }?.substring(0, 1)?.toUpperCase() ?: ""

        return "$firstLetter$lastLetter".takeIf { it.isNotBlank() }
    }

}