package model

data class TeamsInArea(val teams: List<Team>) {

    fun formatTeams() : String {
        val lit = teams.maxOfOrNull { it.id.length } ?: 0
        return teams.joinToString("\n") {
            team -> "ID: ${team.id.padEnd(lit)} | ${team.name}"
        }
    }
}