package model
data class StandingsResponse(val standings: List<Standings>)

data class Standings(val table: List<TablePosition>)

data class TablePosition(val position: String,
                         val team: Team,
                         val points: String)

data class Team(val id: String,
                val name: String)