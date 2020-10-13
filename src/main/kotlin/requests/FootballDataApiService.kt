package requests

import model.*
import model.CompetitionStandings
import model.enums.Status
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FootballDataApiService {

    companion object {
        private const val PLAN = "TIER_ONE"
    }

    @GET("competitions/{competition}")
    fun listCompetition(@Header("X-AUTH-TOKEN") auth: String,
                        @Path("competition") competition: String) : Call<Competition>

    @GET("competitions/{competition}/standings")
    fun listCompetitionStandings(@Header("X-AUTH-TOKEN") auth: String,
                                 @Path("competition") competition: String) : Call<CompetitionStandings>


    @GET("competitions/{competition}/matches")
    fun listCompetitionMatchesByMatchDay(@Header("X-AUTH-TOKEN") auth: String,
                                         @Path("competition") competition: String,
                                         @Query("matchday") matchDay: String? = null) : Call<MatchDay>

    @GET("competitions/{competition}/matches")
    fun listCompetitionMatchesByStatus(@Header("X-AUTH-TOKEN") auth: String,
                                       @Path("competition") competition: String,
                                       @Query("status") status: Status? = null) : Call<MatchDay>

    @GET("teams")
    fun listTeamsByArea(@Header("X-AUTH-TOKEN") auth: String,
                        @Query("areas") areaId: String? = null) : Call<TeamsResponse>
}