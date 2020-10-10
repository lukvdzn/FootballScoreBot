package requests

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import contains
import model.Competitions
import model.Match
import model.TablePosition
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

object FootballDataRetriever {
    // authentication token for api
    private val token: String = System.getenv("X_AUTH_TOKEN")

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.football-data.org/v2/")
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java,
                    // Utc date time adapter for LocalDateTime
                    JsonDeserializer {
                            json, _, _ -> ZonedDateTime.parse(json.asJsonPrimitive.asString).toLocalDateTime()
                    }
                )
                .create()))
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

    fun getMatchesByCompetitionAndMatchday(competition: String, matchDay: String) : String {
        val string = if(contains<Competitions>(competition)) {
            service.listCompetitionMatchesByMatchDay(token, competition, matchDay)
                .execute()
                .let { response ->
                    if(response.isSuccessful) {
                        response.body()
                            ?.run {
                                matches.groupBy { it.utcDate.toLocalDate() }
                                    .map { (date: LocalDate, m: List<Match>) -> "$date\n ${m.joinToString("\n")}\n"}
                                    .joinToString("\n")
                                } ?: "Something unexpected happened with the Response"
                    } else response.errorBody()!!.string()
                }
        } else "Competition \"$competition\" does not exist or is not included in current tier"

        return "`$string`"
    }
}

fun main() {
    val s = FootballDataRetriever.getMatchesByCompetitionAndMatchday("PD", "6")
    print(s)
}