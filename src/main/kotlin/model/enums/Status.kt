package model.enums

enum class Status {
    SCHEDULED,
    CANCELED,
    POSTPONED,
    SUSPENDED,
    IN_PLAY, // live
    PAUSED, // live
    AWARDED,
    FINISHED
}