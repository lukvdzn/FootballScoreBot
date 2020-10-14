package model

import com.google.gson.annotations.SerializedName
import model.enums.StandingsType

data class TablePosition(@SerializedName("position") val position: String,
                         @SerializedName("team") val team: Team,
                         @SerializedName("points") val points: String)

data class Standings(@SerializedName("stage") val stage: String,
                     @SerializedName("type") val type: StandingsType,
                     @SerializedName("group") val group: String?,
                     @SerializedName("table") val table: List<TablePosition>)

data class CompetitionStandings(@SerializedName("competition") val competition: Competition,
                                @SerializedName("standings") val standings: List<Standings>) {

    // Either companion vals or Gson Expose() annotation needed, Gson includes all fields from instance for deserialization
    companion object {
        private const val posTeamTemplate: String = "| %s. %s "
        private const val pointsTemplate: String = "| %s |"
    }

    private fun formatSingleTable(table: List<TablePosition>, ltm: Int): String {
        // fixed pointsTemplate Length
        val fpl = 7
        val borHor = "_".repeat(ltm + fpl)

        return "$borHor\n" + table.joinToString("\n") {
            String.format(posTeamTemplate, it.position, it.team.name).padEnd(ltm)  +
                    String.format(pointsTemplate, it.points.padEnd(3))
        } + "\n$borHor"
    }

    fun formatStandings() : String {
        // standings include TOTAL, HOME, AWAY tables and groups for european/world club competitions
        val standings: List<Standings> = standings.filter { it.type == StandingsType.TOTAL }
        // longest team name to determine horizontal line length for printed table
        val ltm: Int = standings.map { it.table }
                .flatten()
                .maxOfOrNull { String.format(posTeamTemplate, it.position, it.team.name).length } ?: 0

        return "${competition.name}\n" + standings.joinToString(separator = "\n") {
            "${it.group ?: ""}\n${formatSingleTable(it.table, ltm)}\n" }
    }
}