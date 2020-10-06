import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.MessageEvent

fun main(args: Array<String>) {
    val client: GatewayDiscordClient = DiscordClientBuilder.create(args[0]).build().login().block()!!
    client.eventDispatcher.on(MessageEvent::class.java)
        .map { (it as MessageCreateEvent).message}
        .filter { it.author.map { user: User? ->  !user!!.isBot }.orElse(false)}
        .filter { it.content == "!ping" }
        .flatMap(Message::getChannel)
        .flatMap { it.createMessage("Pong!")}
        .subscribe()

    client.onDisconnect().block()
}