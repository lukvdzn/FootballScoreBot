package model

data class TeamsResponse(val teams: List<Team>) {
    fun formatTeams() : String {
        return teams.joinToString("\n") {
            team -> "Id:${team.id}|${team.name}"
        }
    }
}