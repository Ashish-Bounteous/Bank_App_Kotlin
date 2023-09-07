package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {
    fun currentDateTimeString(): String{
        val currentDateTime = LocalDateTime.now()

        // Define a custom date and time format
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a")

        // Format the date and time using the formatter
        return currentDateTime.format(formatter)
    }
}