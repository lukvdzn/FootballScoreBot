package model

import com.google.gson.annotations.SerializedName

data class Team(@SerializedName("id") val id: String,
                @SerializedName("name") val name: String) {
    override fun toString() = name
}

data class TablePosition(@SerializedName("position") val position: String,
                         @SerializedName("team") val team: Team,
                         @SerializedName("points") val points: String)

enum class StandingsType {
    TOTAL,
    HOME,
    AWAY
}

data class Standings(@SerializedName("stage") val stage: String,
                     @SerializedName("type") val type: StandingsType,
                     @SerializedName("group") val group: String,
                     @SerializedName("table") val table: List<TablePosition>)

data class StandingsResponse(@SerializedName("competition") val competition: Competition,
                             @SerializedName("standings") val standings: List<Standings>) {

    private fun formatSingleTable(table: List<TablePosition>): String {
        val preSep: List<String> = table.map { " ${it.position}. ${it.team.name} " }
        val postSep: List<String> = table.map { " ${it.points} " }

        // find longest pre- and postSep string for padding
        val longestPreSep: Int = preSep.maxOfOrNull { it.length } ?: 0
        val longestPostSep: Int = postSep.maxOfOrNull { it.length } ?: 0

        val borHor = "_".repeat(longestPreSep + longestPostSep + 3)
        var format = "$borHor\n"
        for(i in table.indices) {
            format += "|${preSep[i].padEnd(longestPreSep)}|${postSep[i].padEnd(longestPostSep)}|\n"
        }

        return format + borHor
    }

    fun formatStandings() : String {
        // standings include TOTAL, HOME, AWAY
        return "${competition.name}\n\n" + if(standings.size == 3) {
            formatSingleTable(standings[0].table)
        } else { // competition includes groups, stages
            val standings: List<Standings> = standings.filter { it.type == StandingsType.TOTAL }
            standings.joinToString(separator = "\n") { s -> "${s.group}\n${formatSingleTable(s.table)}\n" }
        }
    }
}