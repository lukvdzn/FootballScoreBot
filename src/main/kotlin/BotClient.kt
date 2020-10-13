import command.CommandHandler
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent

object BotClient {
    private lateinit var client: GatewayDiscordClient
    private lateinit var commandHandler: CommandHandler

    fun login() {
        client = DiscordClientBuilder.create(System.getenv("DC_TOKEN")).build().login().block()!!
        commandHandler = CommandHandler()
        listenToMessages()
        client.onDisconnect().block()
    }

    private fun listenToMessages() {
        client.eventDispatcher
                .on(MessageCreateEvent::class.java)
                .filter { it.message.author.map { user -> !user.isBot }.orElse(false) }
                .subscribe { commandHandler.handleCommand(it)}
    }
}

fun main() {
    BotClient.login()
}