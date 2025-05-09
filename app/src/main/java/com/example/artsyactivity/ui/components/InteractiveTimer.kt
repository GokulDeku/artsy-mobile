package com.example.artsyactivity.ui.components

import androidx.compose.runtime.*
import androidx.compose.material3.Text
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun InteractiveTimer(addedAtIso: String) {
    var relativeTime by remember { mutableStateOf(formatRelativeTime(addedAtIso)) }

    LaunchedEffect(addedAtIso) {
        while (true) {
            relativeTime = formatRelativeTime(addedAtIso)
            delay(1000)
        }
    }

    Text(text = relativeTime)
}

private fun formatRelativeTime(isoTime: String): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        val addedAt = format.parse(isoTime) ?: return isoTime
        val now = Date()
        val diffInSeconds = (now.time - addedAt.time) / 1000

        when {
            diffInSeconds < 60 -> plural(diffInSeconds, "second")
            diffInSeconds < 3600 -> plural(diffInSeconds / 60, "minute")
            diffInSeconds < 86400 -> plural(diffInSeconds / 3600, "hour")
            else -> plural(diffInSeconds / 86400, "day")
        }
    } catch (_: Exception) {
        isoTime
    }
}

private fun plural(value: Long, unit: String): String {
    val rounded = value.toInt()
    return if (rounded.toLong() == 1L) "1 $unit ago" else "$rounded ${unit}s ago"
}
