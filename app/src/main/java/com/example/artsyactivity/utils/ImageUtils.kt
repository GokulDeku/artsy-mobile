import com.example.artsyactivity.R

fun String.toArtistImage(): Any {
    return if (this == "/artsy_logo.svg") {
        R.drawable.artsy_logo
    } else {
        this
    }
}