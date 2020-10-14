package command

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent

class CommandReply(private val event: MessageCreateEvent) {
    companion object {
        const val EMPTY_COMMAND = "N/A"
        const val PLAIN_TEXT_TEMPLATE = "```\n%s```"
        const val CSS_SOLARIZED_TEXT_TEMPLATE = "```CSS\n%s```"
        const val DISCORD_MESSAGE_CHAR_LIMIT = 2000
    }

    private val message: Message = event.message
    private val channel = message.channel.block()!!

    fun reply(text: String, colorTemplate: String = PLAIN_TEXT_TEMPLATE) {
        var message = text
        do {
            val format = String.format(colorTemplate, message.take(DISCORD_MESSAGE_CHAR_LIMIT - colorTemplate.length))
            channel.createMessage(format).subscribe()
            message = message.drop(DISCORD_MESSAGE_CHAR_LIMIT - colorTemplate.length)
        } while (message.isNotEmpty())
    }

    // drop main command
    fun subCommands() = (message.content.split(" ") + listOf(EMPTY_COMMAND, EMPTY_COMMAND)).toList()
        .drop(1)
        .map { it.toUpperCase() }
}