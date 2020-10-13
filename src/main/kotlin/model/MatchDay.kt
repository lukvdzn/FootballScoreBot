package model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class MatchDay(@SerializedName("competition") val competition: Competition,
                    @SerializedName("matches") val matches: List<Match>) {

    private fun formatMatchesOnDay(date: LocalDate, list: List<Match>, lht: Int, lat: Int) : String {
        // Score has the form "xx:xx" regardless of scoreline or time
        val fixScoreLength = 5
        val borHor = "_".repeat(lht + lat + fixScoreLength)

        return "${date.toString().padEnd(borHor.length)}\n$borHor\n" + list.joinToString("\n") { match ->
            "|${match.homeTeam.name}".padEnd(lht) +  match.customScoreLine() +
                    "${match.awayTeam.name}|".padStart(lat)
        } + "\n$borHor"
    }

    fun formatMatches() : String {
        val longestHomeTeam = matches.maxOfOrNull { "|${it.homeTeam.name}   ".length } ?: 0
        val longestAwayTeam = matches.maxOfOrNull { "   ${it.awayTeam.name}|".length } ?: 0
        return matches.groupBy { it.utcDate.toLocalDate() }
                .map { (d: LocalDate, m: List<Match>) -> formatMatchesOnDay(d, m, longestHomeTeam, longestAwayTeam) }
                .joinToString("\n")
    }
}