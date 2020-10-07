import discord4j.core.event.domain.message.MessageCreateEvent

fun interface Command {
    fun execute(xAuthToken: String, event: MessageCreateEvent)
}