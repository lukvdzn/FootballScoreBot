package requests

import model.Competition
import model.MatchDay
import model.StandingsResponse
import model.Status
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FootballDataApiService {
    @GET("competitions/{competition}/")
    fun listCompetition(@Header("X-AUTH-TOKEN") auth: String,
                        @Path("competition") competition: String) : Call<Competition>

    @GET("competitions/{competition}/standings")
    fun listCompetitionStandings(@Header("X-AUTH-TOKEN") auth: String,
                                 @Path("competition") competition: String) : Call<StandingsResponse>


    @GET("competitions/{competition}/matches/")
    fun listCompetitionMatchesByMatchDay(@Header("X-AUTH-TOKEN") auth: String,
                                         @Path("competition") competition: String,
                                         @Query("matchday") matchDay: String? = null) : Call<MatchDay>

    @GET("competitions/{competition}/matches/")
    fun listCompetitionMatchesByStatus(@Header("X-AUTH-TOKEN") auth: String,
                                       @Path("competition") competition: String,
                                       @Query("status") status: Status? = null) : Call<MatchDay>
}