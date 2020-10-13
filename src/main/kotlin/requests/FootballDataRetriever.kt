package requests

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import contains
import model.*
import model.CompetitionStandings
import model.enums.Areas
import model.enums.Status
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime

/** Football data provided by the Football-Data.org API */

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

    private fun <T> execute(call: Call<T>, format: T.() -> String) : String {
        return call.execute().let { if(it.isSuccessful) it.body()!!.format() else it.errorBody()!!.string() }
    }

    fun getStandingsByCompetition(competition: String) : String {
        return execute(service.listCompetitionStandings(token, competition),
                CompetitionStandings::formatStandings)
    }

    fun getMatchesByCompetitionAndMatchday(competition: String, matchDay: String) : String {
        return execute(service.listCompetitionMatchesByMatchDay(token, competition, matchDay),
                MatchDay::formatMatches)
    }

    fun getMatchesByCompetitionAndStatus(competition: String, status: Status?) : String {
        return execute(service.listCompetitionMatchesByStatus(token, competition, status),
                MatchDay::formatMatches)
    }

    private fun getCurrentMatchday(competition: String) : String {
        return execute(service.listCompetition(token, competition), Competition::getMatchday)
    }

    fun getMatchesByCompetitionAndCurrentMatchday(competition: String) : String {
        return getCurrentMatchday(competition).let {
            // Matchday has to be integer
            it.toIntOrNull()?.run { getMatchesByCompetitionAndMatchday(competition, it) } ?: it
        }
    }

    fun getTeamsByArea(area: String) : String {
        var areaId: String = area
        if(contains<Areas>(area)) {
            areaId = enumValueOf<Areas>(area).id
        }

        return execute(service.listTeamsByArea(token, areaId), TeamsResponse::formatTeams)
    }
}