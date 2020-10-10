package model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class MatchDay(@SerializedName("competition") val competition: Competition,
                    @SerializedName("matches") val matches: List<Match>) {

    private fun formatMatchesOnDay(date: LocalDate, list: List<Match>) : String {
        val homeTeams = list.map { "|${it.homeTeam}   " }
        val awayTeams = list.map { "   ${it.awayTeam}|" }
        val longestHomeTeam = homeTeams.maxOfOrNull { it.length } ?: 0
        val longestAwayTeam = awayTeams.maxOfOrNull { it.length } ?: 0

        val borHor = "_".repeat(longestHomeTeam + longestAwayTeam + 4)
        var format = "$date\n$borHor\n"

        for(i in list.indices) {
            format += homeTeams[i].padEnd(longestHomeTeam) +
                    list[i].customScoreLine() +
                    awayTeams[i].padStart(longestAwayTeam) +
                    "\n"
        }
        return format + borHor
    }

    fun formatMatches() : String {
        return matches.groupBy { it.utcDate.toLocalDate() }
                .map { (date: LocalDate, m: List<Match>) -> formatMatchesOnDay(date, m)}
                .joinToString("\n\n")
    }
}