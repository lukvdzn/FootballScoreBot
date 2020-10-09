package model

import com.google.gson.annotations.SerializedName

data class Result(@SerializedName("homeTeam") val homeTeam: Int?,
                  @SerializedName("awayTeam") val awayTeam: Int?)

data class Score(@SerializedName("winner") val winner: String,
                 @SerializedName("fullTime") val fullTimeResult: Result,
                 @SerializedName("halfTime") val halfTimeResult: Result,
                 @SerializedName("extraTime") val extraTimeResult: Result,
                 @SerializedName("penalties") val penaltiesResult: Result)

data class Match(@SerializedName("id") val id: String,
                 @SerializedName("season") val season: Season,
                 @SerializedName("utcDate") val utcDate: String,
                 @SerializedName("status") val status: String,
                 @SerializedName("matchday") val matchDay: String,
                 @SerializedName("stage") val stage: String,
                 @SerializedName("group") val group: String,
                 @SerializedName("score") val score: Score,
                 @SerializedName("homeTeam") val homeTeam: Team,
                 @SerializedName("awayTeam") val awayTeam: Team)
