package requests

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import contains
import model.*
import model.enums.Areas
import model.enums.Status
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime

/** Football data provided by the Football-Data.org API */

object FootballDataRetriever {
    // set default header for all requests to the football data api
    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor { chain ->
        // authentication token for api
        val token: String = System.getenv("X_AUTH_TOKEN")
        val request = chain.request().newBuilder().addHeader("X-AUTH-TOKEN", token).build()
        chain.proceed(request)
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.football-data.org/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                        .registerTypeAdapter(LocalDateTime::class.java,
                                // Utc date time adapter for LocalDateTime
                                JsonDeserializer { json, _, _ ->
                                    ZonedDateTime.parse(json.asJsonPrimitive.asString).toLocalDateTime()
                                }
                        )
                        .create())
        )
        .build()

    private val service: FootballDataApiService = retrofit.create(FootballDataApiService::class.java)

    private fun <T> execute(call: Call<T>, format: T.() -> String) : String {
        return call.execute().let { if(it.isSuccessful) it.body()!!.format() else it.errorBody()!!.string() }
    }

    fun getStandingsByCompetition(competition: String) : String {
        return execute(service.listCompetitionStandings(competition), CompetitionStandings::formatStandings)
    }

    fun getMatchesByCompetitionAndMatchday(competition: String, matchDay: String) : String {
        return execute(service.listCompetitionMatchesByMatchDay(competition, matchDay), MatchDay::formatMatches)
    }

    @Suppress("unused")
    fun getMatchesByCompetitionAndStatus(competition: String, status: Status?) : String {
        return execute(service.listCompetitionMatchesByStatus(competition, status), MatchDay::formatMatches)
    }

    private fun getCurrentMatchday(competition: String) : String {
        return execute(service.listCompetition(competition), Competition::getMatchday)
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
        return execute(service.listTeamsByArea(areaId), TeamsInArea::formatTeams)
    }

    fun getTeam(id: String) : String {
        return execute(service.listTeamById(id), Team::formatTeam)
    }
}