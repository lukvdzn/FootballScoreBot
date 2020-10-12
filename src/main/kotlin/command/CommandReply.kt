package command

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent

class CommandReply(private val event: MessageCreateEvent) {
    companion object {
        const val EMPTY_COMMAND = "N/A"
        const val PLAIN_TEXT_TEMPLATE = "```\n%s```"
        const val CSS_SOLARIZED_TEXT_TEMPLATE = "```CSS\n%s```"
    }

    private val message: Message = event.message
    private val channel = message.channel.block()!!

    fun reply(text: String, colorTemplate: String = PLAIN_TEXT_TEMPLATE) {
        channel.createMessage(String.format(colorTemplate, text)).subscribe()
    }

    // sub command may not exist -> Add another element "N/A" to prevent exception
    fun subCommand() = (message.content.split(" ") + listOf(EMPTY_COMMAND))[1].toUpperCase()
    // drop main command
    fun subCommands() = (message.content.split(" ") + listOf(EMPTY_COMMAND, EMPTY_COMMAND)).toList()
        .drop(1)
        .map { it.toUpperCase() }
}