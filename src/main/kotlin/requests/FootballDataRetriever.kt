package requests

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import contains
import model.Competitions
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

    fun getStandingsByCompetition(competition: String) : String {
        val string = if(contains<Competitions>(competition)) {
            service.listCompetitionStandings(token, competition)
                .execute()
                .body()
                ?.formatStandings()
                ?: "Something unexpected happened with the Response"
        } else "Competition \"$competition\" does not exist or is not included in current tier"

        return "`$string`"
    }

    fun getMatchesByCompetitionAndMatchday(competition: String, matchDay: String) : String {
        val string = if(contains<Competitions>(competition)) {
            service.listCompetitionMatchesByMatchDay(token, competition, matchDay)
                .execute()
                .let {
                    if(it.isSuccessful) {
                        it.body()?.formatMatches() ?: "Something unexpected happened with the Response"
                    } else it.errorBody()!!.string()
                }
        } else "Competition \"$competition\" does not exist or is not included in current tier"

        return "`$string`"
    }
}