package model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Result(@SerializedName("homeTeam") val homeTeam: Int = 0,
                  @SerializedName("awayTeam") val awayTeam: Int = 0) {
    override fun toString() = "$homeTeam:$awayTeam"
}

data class Score(@SerializedName("fullTime") val fullTimeResult: Result,
                 @SerializedName("halfTime") val halfTimeResult: Result,
                 @SerializedName("extraTime") val extraTimeResult: Result,
                 @SerializedName("penalties") val penaltiesResult: Result) {
    override fun toString() = fullTimeResult.toString()
}

data class Match(@SerializedName("id") val id: String,
                 @SerializedName("season") val season: Season,
                 @SerializedName("utcDate") val utcDate: LocalDateTime,
                 @SerializedName("status") val status: Status,
                 @SerializedName("matchday") val matchDay: String,
                 @SerializedName("stage") val stage: String,
                 @SerializedName("group") val group: String,
                 @SerializedName("score") val score: Score,
                 @SerializedName("homeTeam") val homeTeam: Team,
                 @SerializedName("awayTeam") val awayTeam: Team) {
    override fun toString(): String {
        return """$homeTeam ${if(status == Status.PAUSED || status == Status.IN_PLAY || status == Status.FINISHED) 
            score.toString() else utcDate.toLocalTime().toString()} $awayTeam""".trimMargin()
    }
}