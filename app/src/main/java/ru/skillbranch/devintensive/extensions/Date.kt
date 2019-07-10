package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var differenceDate = date.time - time
    val value = differenceDate.absoluteValue

    val text = when {
        value <= 1 * SECOND -> return "только что"
        value <= 45 * SECOND -> "несколько секунд"
        value <= 75 * SECOND -> "минуту"
        value <= 45 * MINUTE -> TimeUnits.MINUTE.plural(value / MINUTE)
        value <= 75 * MINUTE -> "час"
        value <= 22 * HOUR -> TimeUnits.HOUR.plural(value / HOUR)
        value <= 360 * DAY -> TimeUnits.DAY.plural(value / DAY)

        else -> return if (differenceDate > 0) "более года назад" else "более чем через год"
    }
    return if (differenceDate > 0) "$text назад" else "через $text"
}

enum class TimeUnits {
    SECOND {
        private val secondPlural = listOf("секунда", "секунду", "секунды", "секунд")
        override fun plural(value: Long): String {
            return when (value % 10) {
                0L -> "$value ${secondPlural[3]}"
                1L -> "$value ${secondPlural[1]}"
                in 2..4 -> "$value ${secondPlural[2]}"
                else -> "$value ${secondPlural[3]}"
            }
        }
    },
    MINUTE {
        private val minutePlural = listOf("минута", "минуту", "минуты", "минут")
        override fun plural(value: Long): String {
            return when (value % 10) {
                0L -> "$value ${minutePlural[3]}"
                1L -> "$value ${minutePlural[1]}"
                in 2..4 -> "$value ${minutePlural[2]}"
                else -> "$value ${minutePlural[3]}"
            }
        }
    },
    HOUR {
        private val hourPlural = listOf("час", "часа", "часов")
        override fun plural(value: Long): String {
            return when (value % 10) {
                0L -> "$value ${hourPlural[2]}"
                1L -> "$value ${hourPlural[0]}"
                in 2..4 -> "$value ${hourPlural[1]}"
                else -> "$value ${hourPlural[2]}"
            }
        }
    },
    DAY {
        private val hourPlural = listOf("день", "дня", "дней")
        override fun plural(value: Long): String {
            return when (value % 10) {
                0L -> "$value ${hourPlural[2]}"
                1L -> "$value ${hourPlural[0]}"
                in 2..4 -> "$value ${hourPlural[1]}"
                else -> "$value ${hourPlural[2]}"
            }
        }
    };

    abstract fun plural(value: Long): String

}