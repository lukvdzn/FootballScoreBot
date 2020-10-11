package command

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent

class CommandReply(private val event: MessageCreateEvent) {
    companion object {
        val EMPTY_COMMAND = "N/A"
    }
    private val message: Message = event.message
    private val channel = message.channel.block()!!

    fun reply(text: String) {
        channel.createMessage(text).subscribe()
    }

    // sub command may not exist -> Add another element "N/A" to prevent exception
    fun subCommand() = (message.content.split(" ") + listOf(EMPTY_COMMAND))[1].toUpperCase()
    // drop main command
    fun subCommands() = (message.content.split(" ") + listOf(EMPTY_COMMAND, EMPTY_COMMAND)).toList()
        .drop(1)
        .map { it.toUpperCase() }
}