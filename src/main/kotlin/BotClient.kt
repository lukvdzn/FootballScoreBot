import com.google.gson.Gson
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.MessageEvent
import okhttp3.OkHttpClient
import okhttp3.Request

private val commands: Map<String, Command> = mapOf(
    Pair("standings", Command { xAuthToken, event ->
        var comp: String = event.message.content.split(" ")[1].toUpperCase()
        comp = Competitions.valueOf(comp).id
        event.message.channel
            .block()
            .createMessage(getStandingsByCompetition(xAuthToken, comp))
            .block()
    })
)

fun getStandingsByCompetition(authToken: String, competition: String) : String {
    val client = OkHttpClient()
    val req = Request.Builder()
        .addHeader("X-Auth-Token", authToken)
        .url("https://api.football-data.org/v2/competitions/${competition}/standings")
        .build()

    val string = client.newCall(req).execute().use { response ->

        response.body?.run {
            if(response.isSuccessful) {
                val table: List<TablePosition> = Gson()
                    .fromJson(this.string(), StandingsResponse::class.java)
                    .standings[0]
                    .table

                val longestSeq: Int = table.map { "${it.position}. ${it.team.name}".length }.maxOrNull() ?: 0
                table.joinToString(separator = "\n") { tablePosition ->
                    val prefix = "${tablePosition.position}. ${tablePosition.team.name}"
                    "$prefix${" ".repeat(longestSeq - prefix.length)} | ${tablePosition.points}"
                }
            }
            else this.string()
        } ?: "Something unexpected happened with the Response"
    }

    return "`${string}`"
}


fun main(args: Array<String>) {
    val client: GatewayDiscordClient = DiscordClientBuilder.create(args[0]).build().login().block()!!
    client.eventDispatcher
        .on(MessageEvent::class.java)
        .subscribe { event ->
            val eventMessage = event as MessageCreateEvent
            val content = eventMessage.message.content

            for ((command, commandExec) in commands) {
                if (content.startsWith("!$command")) {
                    commandExec.execute(args[1], eventMessage)
                    break
                }
            }
        }
    client.onDisconnect().block()
}

/*
fun main(args: Array<String>) {
    val table: List<TablePosition> = getStandings().standings[0].table
    for (tablePosition: TablePosition in table) {
        println("${tablePosition.position}. ${tablePosition.team.name} - ${tablePosition.points}")
    }
}*/