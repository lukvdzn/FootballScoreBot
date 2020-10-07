import com.google.gson.Gson
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.MessageEvent
import okhttp3.OkHttpClient
import okhttp3.Request


fun getStandingsByCompetition(authToken: String, competition: String) : String {
    val client = OkHttpClient()
    val req = Request.Builder()
        .addHeader("X-Auth-Token", authToken)
        .url("https://api.football-data.org/v2/competitions/{competition}/standings")
        .build()

    return client.newCall(req).execute().use { response ->
        val table: List<TablePosition> = Gson()
            .fromJson(response.body!!.string(), StandingsResponse::class.java)
            .standings[0]
            .table
        val longestSeq: Int = table.map { "${it.position}. ${it.team.name}".length }.maxOrNull() ?: 0
        table.joinToString(separator = "\n") { tablePosition ->
            val prefix: String = "${tablePosition.position}. ${tablePosition.team.name}"
            "$prefix${" ".repeat(longestSeq - prefix.length)} | ${tablePosition.points}"
        }
    }
}


fun main(args: Array<String>) {
    val client: GatewayDiscordClient = DiscordClientBuilder.create(args[0]).build().login().block()!!
    client.eventDispatcher.on(MessageEvent::class.java)
        .map { (it as MessageCreateEvent).message }
        .filter { it.author.map { user: User? -> !user!!.isBot }.orElse(false) }
        .filter {
            it.content.run {
                val v = this.split(" ")
                v.size > 1 && v[0] == "!standings" && v[1] == "pl"
            }
        }
        .flatMap(Message::getChannel)
        .flatMap { it.createMessage("`${getStandingsByCompetition(args[1], "pl")}`") }
        .subscribe()

    client.onDisconnect().block()
}

/*
fun main(args: Array<String>) {
    val table: List<TablePosition> = getStandings().standings[0].table
    for (tablePosition: TablePosition in table) {
        println("${tablePosition.position}. ${tablePosition.team.name} - ${tablePosition.points}")
    }
}*/