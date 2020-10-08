package requests

import com.google.gson.Gson
import model.Competition
import model.StandingsResponse
import model.TablePosition
import model.contains
import okhttp3.OkHttpClient
import okhttp3.Request

fun getStandingsByCompetition(competition: String) : String {
    val client = OkHttpClient()
    val authToken = System.getenv("X_AUTH_TOKEN")
    val comp: String = if (contains<Competition>(competition)) Competition.valueOf(competition).name else competition

    val req = Request.Builder()
            .addHeader("X-Auth-Token", authToken)
            .url("https://api.football-data.org/v2/competitions/${comp}/standings")
            .build()

    val string = client.newCall(req).execute().use { response ->
        response.body?.run {
            if(response.isSuccessful) {
                val table: List<TablePosition> = Gson()
                        .fromJson(this.string(), StandingsResponse::class.java)
                        .standings[0]
                        .table

                val longestSeq: Int = table.map { "${it.position}. ${it.team.name}".length }.maxOrNull() ?: 0
                table.joinToString(separator = "\n") { tp ->
                    val prefix = "${tp.position}. ${tp.team.name}"
                    "${prefix.padEnd(longestSeq)} | ${tp.points}"
                }
            }
            else this.string()
        } ?: "Something unexpected happened with the Response"
    }

    return "`${string}`"
}