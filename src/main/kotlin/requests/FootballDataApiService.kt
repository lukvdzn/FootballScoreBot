package requests

import model.*
import model.CompetitionStandings
import model.enums.Status
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FootballDataApiService {

    @GET("competitions/{competition}")
    fun listCompetition(@Path("competition") competition: String) : Call<Competition>

    @GET("competitions/{competition}/standings")
    fun listCompetitionStandings(@Path("competition") competition: String) : Call<CompetitionStandings>


    @GET("competitions/{competition}/matches")
    fun listCompetitionMatchesByMatchDay(@Path("competition") competition: String,
                                         @Query("matchday") matchDay: String? = null) : Call<MatchDay>

    @GET("competitions/{competition}/matches")
    fun listCompetitionMatchesByStatus(@Path("competition") competition: String,
                                       @Query("status") status: Status? = null) : Call<MatchDay>

    @GET("teams")
    fun listTeamsByArea(@Query("areas") areaId: String? = null) : Call<TeamsInArea>

    @GET("teams/{id}")
    fun listTeamById(@Path("id") id: String) : Call<Team>
}