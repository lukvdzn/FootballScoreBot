package requests

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import contains
import model.*
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

    private fun <T> getByCompetition(competition: String, call: Call<T>, format: T.() -> String) : String {
        return if(contains<Competitions>(competition)) {
                    call.execute()
                    .let { if(it.isSuccessful) it.body()!!.format() else it.errorBody()!!.string() }
        } else "Competition \"$competition\" does not exist or is not included in current tier"
    }

    fun getStandingsByCompetition(competition: String) : String {
        return getByCompetition(competition, service.listCompetitionStandings(token, competition),
                StandingsResponse::formatStandings)
    }

    fun getMatchesByCompetitionAndMatchday(competition: String, matchDay: String) : String {
        return getByCompetition(competition, service.listCompetitionMatchesByMatchDay(token, competition, matchDay),
                MatchDay::formatMatches)
    }

    fun getMatchesByCompetitionAndStatus(competition: String, status: Status?) : String {
        return getByCompetition(competition, service.listCompetitionMatchesByStatus(token, competition, status),
                MatchDay::formatMatches)
    }

    private fun getCurrentMatchday(competition: String) : String {
        return getByCompetition(competition, service.listCompetition(token, competition), Competition::getMatchday)
    }

    fun getMatchesByCompetitionAndCurrentMatchday(competition: String) : String {
        val currentMatchDay = getCurrentMatchday(competition)
        return getMatchesByCompetitionAndMatchday(competition, currentMatchDay)
    }
}