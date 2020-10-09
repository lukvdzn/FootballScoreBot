package requests

import contains
import model.Competitions
import model.TablePosition
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballDataRetriever {
    // authentication token for api
    private val token: String = System.getenv("X_AUTH_TOKEN")

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.football-data.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: FootballDataApiService = retrofit.create(FootballDataApiService::class.java)

    fun getStandingsByCompetition(competition: String) : String {
        val string = if(contains<Competitions>(competition)) {
            service.listCompetitionStandings(token, competition)
                .execute()
                .body()
                ?.standings
                ?.run {
                    val table: List<TablePosition> = this[0].table
                    // find longest sequence in
                    val longestSeq: Int = table.map { "${it.position}. ${it.team.name}".length }.maxOrNull() ?: 0
                    table.joinToString(separator = "\n") { tp ->
                        val prefix = "${tp.position}. ${tp.team.name}"
                        "${prefix.padEnd(longestSeq)} | ${tp.points}"
                    }
                } ?: "Something unexpected happened with the Response"

        } else "Competition \"$competition\" does not exist or is not included in current tier"

        return "`$string`"
    }
}