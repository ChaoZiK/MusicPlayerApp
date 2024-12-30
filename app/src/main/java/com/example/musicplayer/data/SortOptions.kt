package com.example.musicplayer.data

enum class SortOption {
    SONG_NAME,
    ARTIST_NAME,
    DURATION
}

sealed class SortDirection {
    abstract val text: String
}

sealed class AlphabeticDirection : SortDirection() {
    data object AToZ : AlphabeticDirection() {
        override val text = "From A to Z"
    }

    data object ZToA : AlphabeticDirection() {
        override val text = "From Z to A"
    }
}

sealed class TimeDirection : SortDirection() {
    data object NewToOld : TimeDirection() {
        override val text = "From new to old"
    }

    data object OldToNew : TimeDirection() {
        override val text = "From old to new"
    }
}

sealed class DurationDirection : SortDirection() {
    data object ShortToLong : DurationDirection() {
        override val text = "From short to long"
    }

    data object LongToShort : DurationDirection() {
        override val text = "From long to short"
    }
}