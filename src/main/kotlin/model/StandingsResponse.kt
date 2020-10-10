package model

import com.google.gson.annotations.SerializedName

data class StandingsResponse(@SerializedName("standings") val standings: List<Standings>)

data class Standings(@SerializedName("table") val table: List<TablePosition>)

data class TablePosition(@SerializedName("position") val position: String,
                         @SerializedName("team") val team: Team,
                         @SerializedName("points") val points: String)

data class Team(@SerializedName("id") val id: String,
                @SerializedName("name") val name: String) {
    override fun toString() = name
}