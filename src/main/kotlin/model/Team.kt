package model

import com.google.gson.annotations.SerializedName

data class Team(@SerializedName("id") val id: String,
                @SerializedName("name") val name: String,
                @SerializedName("activeCompetitions") val activeCompetitions: List<Competition> = emptyList(),
                @SerializedName("website") val website: String?,
                @SerializedName("founded") val founded: String?,
                @SerializedName("venue") val venue: String?,
                @SerializedName("squad") val squad: List<Player> = emptyList()) {
    fun formatTeam() : String {
        val ac: String = activeCompetitions.joinToString(", ") { it.name }
        val sq: String = squad.groupBy { it.position }.map { (pos: String?, players: List<Player>) ->
            "${pos?.plus("s") ?: "Coaches"}: ${players.joinToString(", ") { it.name }}"
        }.joinToString("\n")
        return "$name:\nactive competitions: $ac\nfounded: $founded\nvenue: $venue\n\n$sq"
    }
}